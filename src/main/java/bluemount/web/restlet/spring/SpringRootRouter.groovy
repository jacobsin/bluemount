package bluemount.web.restlet.spring

import bluemount.web.restlet.filters.Filters
import org.restlet.Context
import org.restlet.resource.Directory

import javax.servlet.ServletContext

class SpringRootRouter extends SpringRouter {

  SpringRootRouter(ServletContext servletContext, Context context) {
    super(servletContext, context)
  }

  @Override
  protected void attachRoutes() {
    def web = new SpringUiRouter(servletContext, context)
    attach("/web", web)
    attach("/api", Filters.api(new SpringApiRouter(servletContext, context), context))
    attach("/assets", new Directory(context, "war:///assets/"))
    attachDefault(web)
  }
}
