package bluemount.web.servlet;

import de.devsurf.injection.guice.annotations.GuiceModule;

@GuiceModule
public class ServletModule extends com.google.inject.servlet.ServletModule {
    @Override
    protected void configureServlets() {
        serve("/api/*").with(RestletServlet.class);
        serve("/spring/api/*").with(RestletSpringServlet.class);
    }
}

