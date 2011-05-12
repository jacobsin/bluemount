package bluemount.servlet;

import de.devsurf.injection.guice.annotations.GuiceModule;

@GuiceModule
public class ServletModule extends com.google.inject.servlet.ServletModule {
    @Override
    protected void configureServlets() {
        serve("/rest/v1/*").with(RestletServlet.class);
    }
}

