package bluemount.repository;

import bluemount.model.Project;

import java.util.ArrayList;
import java.util.List;

public class InMemoryProjectRepository implements ProjectRepository {
    private List<Project> projects = new ArrayList<Project>();

    @Override
    public Project create(Project project) {
        projects.add(project);
        return project;
    }

    @Override
    public List<Project> list() {
        return projects;
    }
}
