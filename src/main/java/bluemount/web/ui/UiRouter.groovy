package bluemount.web.ui

import bluemount.web.restlet.guice.GuiceRouter
import bluemount.web.ui.resource.HomeUiResource
import bluemount.web.ui.resource.ProjectsUiResource
import com.google.inject.Injector
import org.restlet.Context

class UiRouter extends GuiceRouter {
  UiRouter(Injector injector, Context context) {
    super(injector, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/projects.html", ProjectsUiResource)
    attach("/index.html", HomeUiResource)
    attachDefault(HomeUiResource)
  }
}
