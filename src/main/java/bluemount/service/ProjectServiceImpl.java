package bluemount.service;

import bluemount.model.Project;
import bluemount.repository.ProjectRepository;
import com.google.inject.Inject;

import java.util.List;

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
