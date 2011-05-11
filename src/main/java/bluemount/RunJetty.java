package bluemount;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class RunJetty {

    public static void main(String[] args) throws Exception {
        Server server = new Server(3000);

        WebAppContext context = new WebAppContext();
        String webapp = webapp();
        context.setDescriptor(webapp + "/WEB-INF/web.xml");
        context.setResourceBase(webapp);
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        server.start();
        server.join();
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
