package bluemount.web.restlet.spring;

import bluemount.web.api.resource.ProjectsApiResource;
import bluemount.web.restlet.Application;
import bluemount.web.ui.resource.ProjectsUiResource;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.ext.servlet.ServletAdapter;
import org.restlet.ext.servlet.internal.ServletWarClient;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class RestletServlet extends HttpServlet {
    private Context context;
    private ServletAdapter adapter;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();

        Component component = new Component();
        component.getClients().add(Protocol.CLAP);
        component.getClients().add(Protocol.FILE);
        context = component.getContext().createChildContext();

        ServletWarClient warClient = new ServletWarClient(component.getContext(), getServletContext());
        component.getClients().add(warClient);

        Application application = new Application(context);
        application.setInboundRoot(new SpringRootRouter(servletContext, context));
        adapter = new ServletAdapter(servletContext);
        adapter.setNext(application);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        adapter.service(request, response);
    }

    private static class SpringRootRouter extends SpringRouter {

        public SpringRootRouter(ServletContext servletContext, Context context) {
            super(servletContext, context);
        }

        @Override
        protected void attachRoutes() {
            attach("/api", new SpringApiRouter(getServletContext(), getContext()));
            attach("/web", new SpringUiRouter(getServletContext(), getContext()));
        }
    }

    private static class SpringApiRouter extends SpringRouter {

        public SpringApiRouter(ServletContext servletContext, Context context) {
            super(servletContext, context);
        }

        @Override
        protected void attachRoutes() {
            attach("/projects/{projectType}", ProjectsApiResource.class);
            attach("/projects", ProjectsApiResource.class);
        }
    }

    private static class SpringUiRouter extends SpringRouter {

        public SpringUiRouter(ServletContext servletContext, Context context) {
            super(servletContext, context);
        }

        @Override
        protected void attachRoutes() {
            attach("/projects.html", ProjectsUiResource.class);
        }
    }
}
