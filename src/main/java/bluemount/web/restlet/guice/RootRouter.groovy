package bluemount.web.restlet.guice

import com.google.inject.Injector
import org.restlet.Context
import bluemount.web.api.ApiRouter
import bluemount.web.ui.UiRouter
import org.restlet.resource.Directory

class RootRouter extends GuiceRouter {
  RootRouter(Injector injector, Context context) {
    super(injector, context)
  }

  @Override
  protected void attachRoutes() {
    def web = new UiRouter(injector, context)
    attach("/web", web)
    attach("/api", new ApiRouter(injector, context))
    attach("/assets", new Directory(context, "war:///assets/"))
    attachDefault(web)
  }
}
