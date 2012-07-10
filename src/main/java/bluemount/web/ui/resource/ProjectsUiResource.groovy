package bluemount.web.ui.resource

import bluemount.core.service.ProjectService
import org.restlet.representation.Representation
import org.restlet.resource.Get
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.inject.Inject

@Scope("prototype")
@Component
public class ProjectsUiResource extends UiResource {
  private final ProjectService service

  @Inject
  public ProjectsUiResource(ProjectService service) {
    this.service = service
  }

  @Get("html")
  public Representation list() {
    freemarker("projects/list", [projects: service.list()])
  }

}

