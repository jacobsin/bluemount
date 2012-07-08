package bluemount.web.api.resource

import bluemount.core.model.CloseSourceProject
import bluemount.core.model.OpenSourceProject
import bluemount.core.model.Project
import bluemount.core.service.ProjectService
import bluemount.web.restlet.jackson.JacksonRepresentation
import org.restlet.resource.Get
import org.restlet.resource.Post
import org.restlet.resource.ServerResource
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.inject.Inject

@Scope("prototype")
@Component
public class ProjectsApiResource extends ServerResource {
  private final ProjectService service

  @Inject
  public ProjectsApiResource(ProjectService service) {
    this.service = service
  }

  @Post("json")
  public Project create(JacksonRepresentation<? extends Project> json) throws IOException {
    service.create(json.getObject(projectClass()))
  }

  @Get("json")
  public List<Project> list() {
    service.list()
  }

  private Class<? extends Project> projectClass() {
    projectClasses()[requestAttributes["projectType"]]
  }

  private def projectClasses() {
    [opensource:OpenSourceProject, closesource:CloseSourceProject]
  }

}

