package bluemount.web.restlet.guice;

import bluemount.web.api.ApiRouter;
import bluemount.web.restlet.Application;
import bluemount.web.restlet.RestletUtils;
import bluemount.web.restlet.jackson.JacksonConverter;
import bluemount.web.ui.UiRouter;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;
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
    Component component = new Component();
    component.getClients().add(Protocol.CLAP);
    context = component.getContext().createChildContext();

    Application application = new Application(context);
    application.setInboundRoot(new RootRouter(injector, context));

    RestletUtils.replaceConverter(org.restlet.ext.jackson.JacksonConverter.class, new JacksonConverter());

    adapter = new ServletAdapter(getServletContext());
    adapter.setNext(application);
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    adapter.service(request, response);
  }

  private static class RootRouter extends GuiceRouter {
    public RootRouter(Injector injector, Context context) {
      super(injector, context);
    }

    @Override
    protected void attachRoutes() {
      attach("/api", new ApiRouter(getInjector(), getContext()));
      attach("/web", new UiRouter(getInjector(), getContext()));
    }
  }

}

