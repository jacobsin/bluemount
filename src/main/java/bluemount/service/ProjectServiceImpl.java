package bluemount.service;

import bluemount.model.Project;
import bluemount.repository.ProjectRepository;
import com.google.inject.Inject;
import de.devsurf.injection.guice.annotations.Bind;

import java.util.List;

@Bind
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;

    @Inject
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project create(Project project) {
        return projectRepository.create(project);
    }

    @Override
    public List<Project> list() {
        return projectRepository.list();
    }
}
