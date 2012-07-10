package bluemount.web.restlet.guice

import bluemount.web.restlet.Application
import bluemount.web.restlet.RestletUtils
import bluemount.web.restlet.jackson.JacksonConverter
import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Singleton
import org.restlet.Component
import org.restlet.Context
import org.restlet.data.Protocol
import org.restlet.ext.servlet.ServletAdapter
import org.restlet.ext.servlet.internal.ServletWarClient

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class RestletServlet extends HttpServlet {
  @Inject
  private Injector injector
  private Context context
  private ServletAdapter adapter

  @Override
  void init() throws ServletException {
    Component component = new Component()

    component.clients.add(Protocol.CLAP)
    component.clients.add(Protocol.FILE)
    context = component.context.createChildContext()

    ServletWarClient warClient = new ServletWarClient(component.context, servletContext)
    component.clients.add(warClient)

    Application application = new Application(context)
    application.setInboundRoot(new RootRouter(injector, context))

    RestletUtils.replaceConverter(org.restlet.ext.jackson.JacksonConverter, new JacksonConverter())

    adapter = new ServletAdapter(servletContext)
    adapter.next = application
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    adapter.service(request, response)
  }
}


