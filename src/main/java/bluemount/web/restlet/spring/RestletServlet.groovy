package bluemount.web.restlet.spring

import bluemount.web.restlet.Application

import org.restlet.Component
import org.restlet.Context
import org.restlet.data.Protocol
import org.restlet.ext.servlet.ServletAdapter
import org.restlet.ext.servlet.internal.ServletWarClient

import javax.inject.Singleton
import javax.servlet.ServletContext
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class RestletServlet extends HttpServlet {
  private Context context
  private ServletAdapter adapter

  @Override
  void init() throws ServletException {
    ServletContext servletContext = servletContext

    Component component = new Component()
    component.clients.add(Protocol.CLAP)
    component.clients.add(Protocol.FILE)
    context = component.context.createChildContext()

    ServletWarClient warClient = new ServletWarClient(component.context, servletContext)
    component.clients.add(warClient)

    Application application = new Application(context)
    application.setInboundRoot(new SpringRootRouter(servletContext, context))
    adapter = new ServletAdapter(servletContext)
    adapter.next = application
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    adapter.service(request, response)
  }
}




