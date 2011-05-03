package bluemount.restlet;

import bluemount.service.ProjectService;
import bluemount.service.ProjectServiceImpl;
import com.google.inject.AbstractModule;

public class RestletModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ProjectService.class).to(ProjectServiceImpl.class);
    }
}
