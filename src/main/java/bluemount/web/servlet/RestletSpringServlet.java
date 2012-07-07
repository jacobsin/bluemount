package bluemount.web.servlet;

import bluemount.web.resource.ProjectsResource;
import bluemount.web.restlet.Application;
import bluemount.web.restlet.SpringRouter;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.ext.servlet.ServletAdapter;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class RestletSpringServlet extends HttpServlet {
    private Context context;
    private ServletAdapter adapter;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();

        Component component = new Component();
        component.getClients().add(Protocol.CLAP);
        context = component.getContext().createChildContext();

        Application application = new Application(context);
        application.setInboundRoot(new SpringRouter(servletContext, context) {

            @Override
            protected void attachRoutes() {
                attach("/projects/{projectType}", ProjectsResource.class);
                attach("/projects", ProjectsResource.class);
            }
        });
        adapter = new ServletAdapter(servletContext);
        adapter.setNext(application);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        adapter.service(request, response);
    }
}
