package bluemount.core.service;

import bluemount.core.model.Project;

import java.util.List;

public interface ProjectService {
    Project create(Project project);

    List<Project> list();
}
