package bluemount.web;

import ch.qos.logback.access.jetty.v7.RequestLogImpl;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class RunJetty {

    private Server server;
    private int port;

    public static void main(String[] args) throws Exception {
        new RunJetty(3000).start(true);
    }

    public RunJetty(int port) throws IOException {
        this.port = port;
        server = new Server();
        server.setConnectors(new Connector[]{connector(false)});

        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(context());
        handlers.addHandler(requestLog());
        server.setHandler(handlers);
    }

    public void start(boolean embedded) throws Exception {
        server.start();
        if (embedded) server.join();
    }

    public void stop() throws Exception {
        if (server.isRunning()) server.stop();
    }

    private static WebAppContext context() throws IOException {
        WebAppContext context = new WebAppContext();
        String webapp = webapp();
        context.setDescriptor(webapp + "/WEB-INF/web.xml");
        context.setResourceBase(webapp);
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        return context;
    }

    private static RequestLogHandler requestLog() {
        RequestLogHandler handler = new RequestLogHandler();
        RequestLogImpl log = new RequestLogImpl();
        handler.setRequestLog(log);
        log.setFileName(System.getProperty("logback.access.config.file", "logback-access.xml"));
        return handler;
    }

    private Connector connector(boolean isProduction) {
        Connector connector = isProduction ? new SelectChannelConnector() : new SocketConnector();
        connector.setPort(port);
        return connector;
    }

    private static String webapp() throws IOException {
        return childDir(new File("."), "webapp").getCanonicalPath();
    }

    private static File childDir(File parent, String name) {
        File[] matches = parent.listFiles((FileFilter) new NameFileFilter(name));
        if (matches.length > 0) return matches[0];
        for (File directory : parent.listFiles((FileFilter) DirectoryFileFilter.INSTANCE)) {
            File match = childDir(directory, name);
            if (match != null) return match;
        }
        return null;
    }
}
