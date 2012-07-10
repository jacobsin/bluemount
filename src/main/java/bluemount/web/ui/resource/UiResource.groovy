package bluemount.web.ui.resource

import bluemount.web.restlet.Application
import org.restlet.data.MediaType
import org.restlet.ext.freemarker.TemplateRepresentation
import org.restlet.resource.ServerResource

class UiResource extends ServerResource {
  protected TemplateRepresentation freemarker(String templateName, Object dataModel=[:]) {
    new TemplateRepresentation("${templateName}.ftl", application.configuration, dataModel, MediaType.TEXT_HTML)
  }

  public Application getApplication() {
    (Application) super.application
  }
}
