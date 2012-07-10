package bluemount.web.restlet.guice

import de.devsurf.injection.guice.annotations.GuiceModule

@GuiceModule
class ServletModule extends com.google.inject.servlet.ServletModule {
  @Override
  protected void configureServlets() {
    serve("/guice/*").with(RestletServlet)
  }
}

