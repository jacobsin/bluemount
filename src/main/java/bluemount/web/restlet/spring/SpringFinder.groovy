package bluemount.web.restlet.spring

import org.restlet.Context
import org.restlet.Request
import org.restlet.Response
import org.restlet.resource.Finder
import org.restlet.resource.ServerResource
import org.springframework.context.ApplicationContext

class SpringFinder extends Finder {
  private ApplicationContext injector

  SpringFinder(ApplicationContext injector, Context context, Class<? extends ServerResource> targetClass) {
    super(context, targetClass)
    this.injector = injector
  }

  @Override
  ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response) {
    injector.getBean(targetClass)
  }
}

