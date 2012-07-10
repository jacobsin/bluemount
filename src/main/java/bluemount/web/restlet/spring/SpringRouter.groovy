package bluemount.web.restlet.spring

import org.restlet.Context
import org.restlet.resource.Finder
import org.restlet.resource.ServerResource
import org.restlet.routing.Router
import org.springframework.context.ApplicationContext
import org.springframework.web.context.WebApplicationContext

import javax.servlet.ServletContext

abstract class SpringRouter extends Router {
  private ApplicationContext applicationContext
  private ServletContext servletContext

  SpringRouter(ServletContext servletContext, Context context) {
    super(context)
    this.servletContext = servletContext
    this.applicationContext = (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)
    attachRoutes()
  }

  @Override
  Finder createFinder(Class<? extends ServerResource> targetClass) {
    new SpringFinder(applicationContext, context, targetClass)
  }

  protected abstract void attachRoutes()

  protected ApplicationContext getApplicationContext() {
    applicationContext
  }

  protected ServletContext getServletContext() {
    servletContext
  }
}

