package bluemount.web.ui

import bluemount.web.restlet.guice.GuiceRouter
import bluemount.web.ui.resource.DocumentDiffUiResource
import bluemount.web.ui.resource.HomeUiResource
import bluemount.web.ui.resource.ProjectsUiResource
import bluemount.web.ui.resource.StaticUiResource
import com.google.inject.Injector
import org.restlet.Context
import org.restlet.routing.Redirector
import org.restlet.routing.Variable

class UiRouter extends GuiceRouter {
  UiRouter(Injector injector, Context context) {
    super(injector, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/packages/diff", DocumentDiffUiResource)
    attach("/projects", ProjectsUiResource)
    attach("/{path}", StaticUiResource).template.variables['path'] = new Variable(Variable.TYPE_URI_PATH)
    attach("/index", HomeUiResource)
    attach("/", new Redirector(context, "/index", Redirector.MODE_CLIENT_PERMANENT))
  }
}
