package bluemount.web.restlet.spring;

import bluemount.web.api.resource.ProjectsApiResource;
import bluemount.web.restlet.Application;
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
public class RestletServlet extends HttpServlet {
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
                attach("/projects/{projectType}", ProjectsApiResource.class);
                attach("/projects", ProjectsApiResource.class);
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
