package bluemount.web.servlet;

import bluemount.web.resource.ProjectsResource;
import bluemount.web.restlet.GuiceRouter;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServletAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class RestletServlet extends HttpServlet {
    @Inject
    private Injector injector;
    private Context context;
    private ServletAdapter adapter;

    @Override
    public void init() throws ServletException {
        context = new Context();
        Application application = new Application();
        application.setContext(context);
        application.setInboundRoot(new GuiceRouter(injector, context) {
            @Override
            protected void attachRoutes() {
                attach("/projects", ProjectsResource.class);
            }
        });
        adapter = new ServletAdapter(getServletContext());
        adapter.setNext(application);
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        adapter.service(request, response);
    }
}
