package bluemount;

import bluemount.resource.FirstServerResource;
import org.restlet.Server;
import org.restlet.data.Protocol;

public class ServiceMain {
    public static void main(String[] args) throws Exception {
        new Server(Protocol.HTTP, 3000, FirstServerResource.class).start();
    }
}
