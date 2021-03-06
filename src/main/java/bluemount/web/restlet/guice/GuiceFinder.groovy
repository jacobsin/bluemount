package bluemount.web.restlet.guice

import com.google.inject.Injector
import org.restlet.Context
import org.restlet.Request
import org.restlet.Response
import org.restlet.resource.Finder
import org.restlet.resource.ServerResource

class GuiceFinder extends Finder {
  private Injector injector

  GuiceFinder(Injector injector, Context context, Class<? extends ServerResource> targetClass) {
    super(context, targetClass)
    this.injector = injector
  }

  @Override
  ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response) {
    injector.getInstance(targetClass)
  }
}

