package bluemount.web.restlet.spring;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;
import org.springframework.context.ApplicationContext;

public class SpringFinder extends Finder {
    private final ApplicationContext injector;

    public SpringFinder(ApplicationContext injector, Context context, Class<? extends ServerResource> targetClass) {
        super(context, targetClass);
        this.injector = injector;
    }

    @Override
    public ServerResource create(Class<? extends ServerResource> targetClass, Request request, Response response) {
        return injector.getBean(targetClass);
    }
}

