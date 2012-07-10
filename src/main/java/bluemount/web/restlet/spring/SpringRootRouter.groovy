package bluemount.web.restlet.spring

import javax.servlet.ServletContext
import org.restlet.Context

class SpringRootRouter extends SpringRouter {

  SpringRootRouter(ServletContext servletContext, Context context) {
    super(servletContext, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/api", new SpringApiRouter(servletContext, context))
    attach("/web", new SpringUiRouter(servletContext, context))
  }
}
