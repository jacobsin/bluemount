package bluemount.service;

import bluemount.model.Project;

import java.util.Arrays;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public List<Project> list() {
        return Arrays.asList(new Project("project a"), new Project("project b"));
    }
}
