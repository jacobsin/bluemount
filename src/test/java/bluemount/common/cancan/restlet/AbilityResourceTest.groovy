package bluemount.common.cancan.restlet

import bluemount.common.cancan.Ability
import bluemount.common.cancan.AccessDeniedException
import bluemount.common.utils.ClassFinder
import bluemount.web.ui.resource.UiResource
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.stubbing.Answer
import org.restlet.Application
import org.restlet.Request
import org.restlet.Response
import org.restlet.data.MediaType
import org.restlet.data.Method
import org.restlet.data.Status
import org.restlet.ext.jackson.JacksonRepresentation
import org.restlet.representation.EmptyRepresentation
import org.restlet.representation.Representation
import org.restlet.representation.StringRepresentation
import org.restlet.service.StatusService

import java.lang.reflect.Modifier

import org.mockito.*
import org.restlet.resource.*

@RunWith(MockitoJUnitRunner)
class AbilityResourceTest {

  @Mock Ability ability
  @Mock Application application
  @Mock StatusService statusService
  @Mock JacksonRepresentation representation
  @Spy Request request = new Request()
  @Spy Response response = new Response(request)
  @Captor ArgumentCaptor<Throwable> throwableCaptor

  Method method
  AbilityResource resource
  TestResource testResource
  def subject = new Object()
  def customSubject = new Object()
  def restletEndPointsAnnotations = [Get.class, Post.class, Put.class, Delete.class, Options.class]
  def status = new Status(Status.CLIENT_ERROR_FORBIDDEN, (Throwable) null)
  def exception = new AccessDeniedException("some-message")
  def view = new View()

  def shouldFail = new GroovyTestCase().&shouldFail

  @Before
  public void before() {
    testResource = new TestResource(subject: subject, customSubject: customSubject)
    resource = new AbilityResource(testResource, ability)
    setupResource(resource)

    Mockito.when(request.getMethod()).thenAnswer({method} as Answer<Method>)
    Mockito.when(ability.authorize(Action.approve, subject)).thenReturn(subject)
    Mockito.when(application.getStatusService()).thenReturn(statusService)
    Mockito.when(statusService.getStatus(throwableCaptor.capture(), Matchers.same(resource))).thenReturn(status)
  }

  private void setupResource(AbilityResource resource) {
    resource.request = request
    resource.response = response
    resource.application = application
  }

  @Test
  public void handleWithPermissionGranted() {
    method = Method.POST

    def result = resource.handle()
    assert result instanceof StringRepresentation
    assert result.text == "success"
    assert result.mediaType == MediaType.APPLICATION_JSON

    Mockito.verify(response, Mockito.never()).setStatus(Mockito.<Status> any())
  }

  @Test
  public void handleWithAccessDenied() {
    method = Method.POST
    Mockito.when(ability.authorize(Action.approve, subject)).thenThrow(exception)

    def result = resource.handle()
    assert result == null
    assert !testResource.invoked

    Mockito.verify(response, Mockito.times(1)).setStatus(status)


    assert throwableCaptor.value instanceof AccessDeniedException
    assert throwableCaptor.value.message == exception.message
  }

  @Test
  public void handleWithAccessGrantedForPublicEndPoint() {
    method = Method.GET

    def result = resource.handle()
    assert result instanceof StringRepresentation
    assert result.text == "success"
    assert result.mediaType == MediaType.APPLICATION_JSON

    Mockito.verify(response, Mockito.never()).setStatus(Mockito.<Status> any())
  }

  @Test
  public void handleWithAccessDeniedForEndPointNotSecured() {
    method = Method.DELETE

    def result = resource.handle()
    assert result == null
    assert !testResource.invoked

    Mockito.verify(response, Mockito.times(1)).setStatus(status)

    assert throwableCaptor.value instanceof AccessDeniedException
    assert throwableCaptor.value.message == "Forbidden"
  }

  @Test
  public void handleWithAccessDeniedForUnsupportedEndPoint() {
    method = Method.OPTIONS

    def result = resource.handle()
    assert result == null

    Mockito.verify(response, Mockito.times(1)).setStatus(status)

    assert throwableCaptor.value instanceof AccessDeniedException
    assert throwableCaptor.value.message == "Forbidden"
  }

  @Test
  def void handleWithNoLoadResourceMethod() {
    method = Method.GET

    def testResource = new TestResourceWithoutLoadResource()
    resource = new AbilityResource(testResource, ability)
    setupResource(resource)

    shouldFail(MissingMethodException) {resource.handle()} == "No signature of method: wallaby.common.cancan.restlet.TestResourceWithoutLoadResource.loadResource() is applicable for argument types: () values: []"
  }

  @Test
  def void handleWithCustomLoadResourceMethod() {
    method = Method.PUT

    Mockito.when(ability.authorize(Action.approve, customSubject)).thenReturn(customSubject)

    def result = resource.handle()
    assert result instanceof StringRepresentation
    assert result.text == "success"
    assert result.mediaType == MediaType.APPLICATION_JSON

    Mockito.verify(response, Mockito.never()).setStatus(Mockito.<Status> any())
    Mockito.verify(ability, Mockito.never()).authorize(Action.approve, subject)
  }

  @Test
  def void handleWithSubject() {
    method = Method.GET

    def testResource = new TestResourceWithSubject(subject: subject)
    resource = new AbilityResource(testResource, ability)
    setupResource(resource)

    def result = resource.handle()
    assert result instanceof StringRepresentation
    assert result.text == "success"
    assert result.mediaType == MediaType.APPLICATION_JSON

    Mockito.verify(response, Mockito.never()).setStatus(Mockito.<Status> any())
    Mockito.verify(ability, Mockito.never()).authorize(Action.approve, subject)
    Mockito.verify(ability).authorize(Action.approve, String)
  }


  @Test
  def void handleWithBothSubjectAndCustomResourceWillUseSubject() {
    method = Method.PUT

    def testResource = new TestResourceWithSubject(subject: subject, customSubject: customSubject)
    resource = new AbilityResource(testResource, ability)
    setupResource(resource)

    def result = resource.handle()
    assert result instanceof StringRepresentation
    assert result.text == "success"
    assert result.mediaType == MediaType.APPLICATION_JSON

    Mockito.verify(response, Mockito.never()).setStatus(Mockito.<Status> any())
    Mockito.verify(ability, Mockito.never()).authorize(Action.approve, subject)
    Mockito.verify(ability, Mockito.never()).authorize(Action.approve, customSubject)
    Mockito.verify(ability).authorize(Action.approve, String)
  }

  @Test
  public void allRestletEndPointsAreSecuredOrExplicitlyPublic() {
    def unsecured = []
    new ClassFinder().findSubclasses("wallaby.web", ServerResource).findAll {c -> c != TestResource && !UiResource.isAssignableFrom(c)}.each {c ->
      unsecured.addAll c.methods.findAll {m ->
        Modifier.isPublic(m.modifiers) && isRestletEndPoint(m) && !(m.isAnnotationPresent(Authorize) || m.isAnnotationPresent(Public))
      }
    }
    assert unsecured.empty, "${unsecured.size()} unsecured restlet endpoints:\n${unsecured.join("\n")}\n"
  }

  @Test
  def void handleEnrichAbilities() {
    method = Method.GET

    Mockito.when(representation.object).thenReturn(view)

    def testResource = new TestResourceWithView(representation: representation)
    resource = new AbilityResource(testResource, ability)
    setupResource(resource)

    assert resource.handle() == representation
    assert view.ability == ability
  }

  @Test
  def void handleSkipsEnrichingAbilitiesWhenViewDoesNotHaveAbility() {
    method = Method.GET
    view = new ViewWithoutAbility()

    Mockito.when(representation.object).thenReturn(view)

    def testResource = new TestResourceWithView(representation: representation)
    resource = new AbilityResource(testResource, ability)
    setupResource(resource)

    assert resource.handle() == representation
  }

  @Test
  def void handleSkipsEnrichingAbilitiesWhenDelegateReturnsNonJacksonRepresentation() {
    method = Method.GET
    view = new ViewWithoutAbility()
    def representation = new EmptyRepresentation()

    def testResource = new TestResourceWithView(representation: representation)
    resource = new AbilityResource(testResource, ability)
    setupResource(resource)

    assert resource.handle() == representation
  }


  boolean isRestletEndPoint(java.lang.reflect.Method method) {
    method.declaredAnnotations.toList().any {declared -> restletEndPointsAnnotations.any {declared.annotationType() == it}}
  }
}

class TestResource extends ServerResource {
  def invoked
  def subject
  def customSubject

  @Delete("json")
  String unsecuredEndPoint() {
    "success"
  }

  @Post("json") @Authorize(Action.approve)
  String securedEndPoint() {
    invoked = true
    "success"
  }

  @Get("json") @Public
  String publicEndPoint() {
    "success"
  }

  @Put("json") @Authorize(value = Action.approve, loadResource = "customLoadResource")
  String securedEndPointWithCustomLoadResource() {
    invoked = true
    "success"
  }

  def loadResource() {
    subject
  }

  def customLoadResource() {
    customSubject
  }
}

class TestResourceWithoutLoadResource extends ServerResource {

  @Get("json") @Authorize(Action.approve)
  String securedEndPoint() {
    "success"
  }
}

class TestResourceWithSubject extends ServerResource {
  def invoked
  def subject
  def customSubject


  @Get("json") @Authorize(value = Action.approve, subject = String)
  String securedEndPoint() {
    invoked = true
    "success"
  }

  @Put("json") @Authorize(value = Action.approve, subject = String, loadResource = "customLoadResource")
  String securedEndPointWithSubjectAndCustomResource() {
    invoked = true
    "success"
  }

  def loadResource() {
    subject
  }

  def customLoadResource() {
    customSubject
  }

}

class TestResourceWithView extends ServerResource {
  def representation

  @Get("json") @Public
  Representation publicEndPoint() {
    return representation
  }
}

class View {
  def ability
}

class ViewWithoutAbility {
}