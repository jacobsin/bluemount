package bluemount.web.ui

import bluemount.web.restlet.guice.GuiceRouter
import bluemount.web.ui.resource.DocumentDiffUiResource
import bluemount.web.ui.resource.HomeUiResource
import bluemount.web.ui.resource.ProjectsUiResource
import bluemount.web.ui.resource.StaticUiResource
import com.google.inject.Injector
import org.restlet.Context

class UiRouter extends GuiceRouter {
  UiRouter(Injector injector, Context context) {
    super(injector, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/packages/diff", DocumentDiffUiResource)
    attach("/projects", ProjectsUiResource)
    attach("/static/{path}", StaticUiResource)
    attach("/index", HomeUiResource)
    attach("/", HomeUiResource)
    attachDefault(HomeUiResource)
  }
}
