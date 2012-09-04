package bluemount.common.cancan.restlet


import bluemount.common.cancan.Ability
import org.restlet.Context
import org.restlet.Request
import org.restlet.Response
import org.restlet.resource.ServerResource
import org.springframework.context.ApplicationContext

class SpringFinder extends bluemount.web.restlet.spring.SpringFinder {

  SpringFinder(ApplicationContext injector, Context context, Class<? extends ServerResource> targetClass) {
    super(injector, context, targetClass)
  }

  @Override
  ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response) {
    new AbilityResource(super.create(targetClass, request, response), injector.getBean(Ability))
  }
}
