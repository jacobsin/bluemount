package bluemount.web.resource;

import bluemount.core.model.Project;
import bluemount.core.service.ProjectService;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import java.util.List;

@Scope("prototype")
public class ProjectsResource extends ServerResource {
    private final ProjectService service;

    @Inject
    public ProjectsResource(ProjectService service) {
        this.service = service;
    }

    @Post("json")
    public Project create(Project project) {
        return service.create(project);
    }

    @Get("json")
    public List<Project> list() {
        return service.list();
    }
}

