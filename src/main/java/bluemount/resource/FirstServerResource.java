package bluemount.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FirstServerResource extends ServerResource {
    @Get
    public String toString() {
        return "hello, world";
    }
}
