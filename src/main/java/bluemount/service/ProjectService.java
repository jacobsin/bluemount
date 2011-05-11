package bluemount.service;

import bluemount.model.Project;

import java.util.List;

public interface ProjectService {
    Project create(Project project);

    List<Project> list();
}
