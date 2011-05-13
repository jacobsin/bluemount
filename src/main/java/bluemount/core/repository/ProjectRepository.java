package bluemount.core.repository;

import bluemount.core.model.Project;

import java.util.List;

public interface ProjectRepository {
    Project create(Project project);

    List<Project> list();
}
