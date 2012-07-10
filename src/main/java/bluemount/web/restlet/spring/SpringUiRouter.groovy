package bluemount.web.restlet.spring

import javax.servlet.ServletContext
import org.restlet.Context
import bluemount.web.ui.resource.ProjectsUiResource

class SpringUiRouter extends SpringRouter {

  SpringUiRouter(ServletContext servletContext, Context context) {
    super(servletContext, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/projects.html", ProjectsUiResource)
  }
}
