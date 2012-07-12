package bluemount.web.restlet.spring

import javax.servlet.ServletContext
import org.restlet.Context
import bluemount.web.ui.resource.ProjectsUiResource
import bluemount.web.ui.resource.HomeUiResource

class SpringUiRouter extends SpringRouter {

  SpringUiRouter(ServletContext servletContext, Context context) {
    super(servletContext, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/projects.html", ProjectsUiResource)
    attach("/index.html", HomeUiResource)
    attachDefault(HomeUiResource)
  }
}
