package bluemount.restlet;

import com.google.inject.Injector;
import org.restlet.Context;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public abstract class GuiceRouter extends Router {
    private final Injector injector;

    public GuiceRouter(Injector injector, Context context) {
        super(context);
        this.injector = injector;
        attachRoutes();
    }

    @Override
    public Finder createFinder(Class<? extends ServerResource> targetClass) {
        return new GuiceFinder(injector, getContext(), targetClass);
    }

    protected abstract void attachRoutes();

    protected Injector getInjector() {
        return injector;
    }
}

