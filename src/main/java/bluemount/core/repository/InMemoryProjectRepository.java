package bluemount.core.repository;

import bluemount.core.model.Project;
import de.devsurf.injection.guice.annotations.Bind;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Bind @Singleton
@Component
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
