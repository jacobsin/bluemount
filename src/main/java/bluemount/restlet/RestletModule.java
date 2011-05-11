package bluemount.restlet;

import bluemount.repository.InMemoryProjectRepository;
import bluemount.repository.ProjectRepository;
import bluemount.service.ProjectService;
import bluemount.service.ProjectServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class RestletModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ProjectService.class).to(ProjectServiceImpl.class);
        bind(ProjectRepository.class).to(InMemoryProjectRepository.class).in(Singleton.class);
    }
}
