package bluemount.web.ui.resource

import bluemount.core.service.ProjectService
import bluemount.web.restlet.Application
import org.restlet.data.MediaType
import org.restlet.ext.freemarker.TemplateRepresentation
import org.restlet.representation.Representation
import org.restlet.resource.Get
import org.restlet.resource.ServerResource
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.inject.Inject

@Scope("prototype")
@Component
class ProjectsUiResource extends ServerResource {
  private final ProjectService service

  @Inject
  ProjectsUiResource(ProjectService service) {
    this.service = service
  }

  @Get("html")
  Representation list() {
    freemarker("projects/list", [projects: service.list()])
  }

  private TemplateRepresentation freemarker(String templateName, Object dataModel) {
    new TemplateRepresentation("${templateName}.ftl", application.configuration, dataModel, MediaType.TEXT_HTML)
  }

  Application getApplication() {
    (Application) super.application
  }

}

