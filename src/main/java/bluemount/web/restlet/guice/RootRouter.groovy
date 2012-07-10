package bluemount.web.restlet.guice

import com.google.inject.Injector
import org.restlet.Context
import bluemount.web.api.ApiRouter
import bluemount.web.ui.UiRouter

class RootRouter extends GuiceRouter {
  RootRouter(Injector injector, Context context) {
    super(injector, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/api", new ApiRouter(injector, context))
    attach("/web", new UiRouter(injector, context))
  }
}
