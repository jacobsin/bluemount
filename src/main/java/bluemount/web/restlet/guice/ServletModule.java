package bluemount.web.restlet.guice;

import de.devsurf.injection.guice.annotations.GuiceModule;

@GuiceModule
public class ServletModule extends com.google.inject.servlet.ServletModule {
    @Override
    protected void configureServlets() {
        serve("/guice/*").with(RestletServlet.class);
    }
}

