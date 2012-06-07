package bluemount.core.service;

import bluemount.core.model.Project;
import bluemount.core.repository.ProjectRepository;
import de.devsurf.injection.guice.annotations.Bind;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Bind
@Component
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private String defaultOwner;

    @Inject
    public ProjectServiceImpl(ProjectRepository projectRepository, @Named("project.defaultOwner") String defaultOwner) {
        this.projectRepository = projectRepository;
        this.defaultOwner = defaultOwner;
    }

    @Override
    public Project create(Project project) {
        if (project.getOwner() == null) project.setOwner(defaultOwner);
        return projectRepository.create(project);
    }

    @Override
    public List<Project> list() {
        return projectRepository.list();
    }
}
