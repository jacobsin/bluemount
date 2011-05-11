package bluemount.repository;

import bluemount.model.Project;

import java.util.List;

public interface ProjectRepository {
    Project create(Project project);

    List<Project> list();
}
