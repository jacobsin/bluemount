package bluemount.web


import ch.qos.logback.access.jetty.v7.RequestLogImpl
import org.apache.commons.io.filefilter.DirectoryFileFilter
import org.apache.commons.io.filefilter.NameFileFilter
import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.bio.SocketConnector
import org.eclipse.jetty.server.handler.HandlerCollection
import org.eclipse.jetty.server.handler.RequestLogHandler
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.webapp.WebAppContext

class RunJetty {

  private Server server
  private int port

  static void main(String[] args) throws Exception {
    new RunJetty(4000).start(true)
  }

  RunJetty(int port) throws IOException {
    this.port = port
    server = new Server()
    server.connectors = [connector(false)].toArray(new Connector[1])

    def handlers = new HandlerCollection()
    handlers.addHandler(context())
    handlers.addHandler(requestLog())
    server.handler = handlers
  }

  void start(boolean embedded) throws Exception {
    server.start()
    if (embedded) server.join()
  }

  void stop() throws Exception {
    if (server.running) server.stop()
  }

  private def context() throws IOException {
    def context = new WebAppContext()
    def webapp = webapp()
    context.descriptor = webapp + "/WEB-INF/web.xml"
    context.resourceBase = webapp
    context.contextPath = "/"
    context.parentLoaderPriority = true
    context
  }

  private def requestLog() {
    def handler = new RequestLogHandler()
    def log = new RequestLogImpl()
    handler.requestLog = log
    log.fileName = System.getProperty("logback.access.config.file", "logback-access.xml")
    handler
  }

  private def connector(boolean isProduction) {
    def connector = isProduction ? new SelectChannelConnector() : new SocketConnector()
    connector.port = port
    connector
  }

  private static def webapp() throws IOException {
    childDir(new File("."), "webapp").getCanonicalPath()
  }

  private static def childDir(File parent, String name) {
    def matches = parent.listFiles((FileFilter) new NameFileFilter(name))
    if (matches) return matches[0]
    for (File directory : parent.listFiles((FileFilter) DirectoryFileFilter.INSTANCE)) {
      File match = childDir(directory, name)
      if (match != null) return match
    }
    null
  }
}
