package bluemount.web.restlet.guice

import com.google.inject.Injector
import org.restlet.Context
import org.restlet.resource.Finder
import org.restlet.resource.ServerResource
import org.restlet.routing.Router

abstract class GuiceRouter extends Router {
  private Injector injector

  GuiceRouter(Injector injector, Context context) {
    super(context)
    this.injector = injector
    attachRoutes()
  }

  @Override
  Finder createFinder(Class<? extends ServerResource> targetClass) {
    new GuiceFinder(injector, context, targetClass)
  }

  protected abstract void attachRoutes()

  protected def getInjector() {
    injector
  }
}

