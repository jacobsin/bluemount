package bluemount.resource;

import bluemount.model.Project;
import bluemount.service.ProjectService;
import com.google.inject.Inject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;

public class ProjectsResource extends ServerResource {
    private final ProjectService service;

    @Inject
    public ProjectsResource(ProjectService service) {
        this.service = service;
    }

    @Get("json")
    public List<Project> list() {
        return service.list();
    }
}

