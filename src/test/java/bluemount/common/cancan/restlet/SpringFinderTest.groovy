package bluemount.common.cancan.restlet

import bluemount.common.cancan.Ability

import groovy.mock.interceptor.StubFor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.restlet.resource.ServerResource
import org.springframework.context.ApplicationContext

@RunWith(MockitoJUnitRunner)
class SpringFinderTest {

  SpringFinder springFinder
  @Mock ApplicationContext applicationContext
  @Mock AbilityResource abilityResource

  @Before
  def void before() {
    springFinder = new SpringFinder(applicationContext, null, null)
  }

  @Test
  def void create() {
    def expectedAbility = new Ability()
    def expectedResource = new MyServerResource()

    Mockito.when(applicationContext.getBean(MyServerResource)).thenReturn(expectedResource)
    Mockito.when(applicationContext.getBean(Ability)).thenReturn(expectedAbility)

    def stub = new StubFor(AbilityResource, true)

    stub.demand.with{
      AbilityResource{ resource, ability ->
        assert ability == expectedAbility
        assert resource == expectedResource
        return abilityResource
      }
    }

    stub.use {
      assert springFinder.create(MyServerResource, null, null) == abilityResource
    }
  }
}

class MyServerResource extends ServerResource{

}