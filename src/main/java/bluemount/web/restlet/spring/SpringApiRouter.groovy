package bluemount.web.restlet.spring

import javax.servlet.ServletContext
import org.restlet.Context
import bluemount.web.api.resource.ProjectsApiResource

class SpringApiRouter extends SpringRouter {

  SpringApiRouter(ServletContext servletContext, Context context) {
    super(servletContext, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/projects/{projectType}", ProjectsApiResource)
    attach("/projects/", ProjectsApiResource)
  }
}
