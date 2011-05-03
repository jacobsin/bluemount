package bluemount.servlet;

import bluemount.restlet.RestletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ServletConfig extends GuiceServletContextListener {

    public ServletConfig() {
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(), new RestletModule());
    }
}
