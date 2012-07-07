package bluemount.web.resource

import bluemount.core.model.CloseSourceProject
import bluemount.core.model.OpenSourceProject
import bluemount.core.model.Project
import bluemount.core.service.ProjectService
import bluemount.web.jackson.JacksonRepresentation
import bluemount.web.restlet.Application
import org.restlet.data.MediaType
import org.restlet.ext.freemarker.TemplateRepresentation
import org.restlet.representation.Representation
import org.restlet.resource.Get
import org.restlet.resource.Post
import org.restlet.resource.ServerResource
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.inject.Inject

@Scope("prototype")
@Component
public class ProjectsResource extends ServerResource {
  private final ProjectService service

  @Inject
  public ProjectsResource(ProjectService service) {
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

  @Get("html")
  public Representation listHtml() {
    freemarker("projects/list", [projects: list()])
  }

  private TemplateRepresentation freemarker(String templateName, Object dataModel) {
    new TemplateRepresentation("${templateName}.ftl", application.configuration, dataModel, MediaType.TEXT_HTML)
  }

  private Class<? extends Project> projectClass() {
    projectClasses()[requestAttributes["projectType"]]
  }

  private def projectClasses() {
    [opensource:OpenSourceProject, closesource:CloseSourceProject]
  }

  public Application getApplication() {
    (Application) super.application
  }

}

