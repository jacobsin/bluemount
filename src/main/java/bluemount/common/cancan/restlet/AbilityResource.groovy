package bluemount.common.cancan.restlet

import bluemount.common.cancan.Ability
import bluemount.common.cancan.AccessDeniedException
import org.restlet.engine.resource.AnnotationInfo
import org.restlet.engine.resource.AnnotationUtils
import org.restlet.ext.jackson.JacksonRepresentation
import org.restlet.representation.Representation
import org.restlet.resource.ServerResource

import java.lang.reflect.Method

class AbilityResource extends ForwardingServerResource {


  private Ability ability

  AbilityResource(ServerResource delegate, Ability ability) {
    this.delegate = delegate
    this.ability = ability
  }

  Representation handle() {
    def annotation = annotation
    if (!annotation) return forbidden('Forbidden')

    def javaMethod = annotation.javaMethod

    if (!isPublic(javaMethod)) {
      def authorize = getAuthorize(javaMethod)
      if (!authorize) return forbidden("Forbidden")

      def action = authorize.value()
      def loadResource = authorize.loadResource()

      def subject = authorize.subject() != Void ? authorize.subject() : delegate."${loadResource}"()

      try {
        ability.authorize(action, subject)
      } catch (AccessDeniedException e) {
        doCatch(e)
        return null
      }
    }
    enrichAbilities(delegate.handle())
  }

  private def enrichAbilities(Representation representation) {
    if (representation instanceof JacksonRepresentation) {
      def view = representation.object
      if (view.hasProperty("ability")) {
        view.ability = ability
      }
    }
    representation
  }

  private getAuthorize(Method javaMethod) {
    javaMethod.getAnnotation(Authorize)
  }

  private Representation forbidden(String message) {
    doCatch(new AccessDeniedException(message))
    null
  }

  private boolean isPublic(Method javaMethod) {
    javaMethod.getAnnotation(Public) != null
  }

  private AnnotationInfo getAnnotation() {
    AnnotationUtils.getAnnotation(AnnotationUtils.getAnnotations(delegate.class), method, query, null, metadataService, converterService)
  }
}
