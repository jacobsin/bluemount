package bluemount.web.resource;

import bluemount.core.model.CloseSourceProject;
import bluemount.core.model.OpenSourceProject;
import bluemount.core.model.Project;
import bluemount.core.service.ProjectService;
import bluemount.web.jackson.JacksonRepresentation;
import com.google.common.collect.Maps;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
public class ProjectsResource extends ServerResource {
    private final ProjectService service;

    @Inject
    public ProjectsResource(ProjectService service) {
        this.service = service;
    }

    @Post("json")
    public Project create(JacksonRepresentation<? extends Project> json) {
        return service.create(json.getObject(projectClass()));
    }

    @Get("json")
    public List<Project> list() {
        return service.list();
    }

    private Class<? extends Project> projectClass() {
        return projectClasses().get((String) getRequestAttributes().get("projectType"));
    }

    private Map<String, Class<? extends Project>> projectClasses() {
        HashMap<String,Class<? extends Project>> classes = Maps.newHashMap();
        classes.put("opensource", OpenSourceProject.class);
        classes.put("closesource", CloseSourceProject.class);
        return classes;
    }

}

