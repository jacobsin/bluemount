package bluemount.web.restlet.spring

import javax.servlet.ServletContext
import org.restlet.Context
import org.restlet.resource.Directory

class SpringRootRouter extends SpringRouter {

  SpringRootRouter(ServletContext servletContext, Context context) {
    super(servletContext, context)
  }

  @Override
  protected void attachRoutes() {
    def web = new SpringUiRouter(servletContext, context)
    attach("/web", web)
    attach("/api", new SpringApiRouter(servletContext, context))
    attach("/assets", new Directory(context, "war:///assets/"))
    attachDefault(web)
  }
}
