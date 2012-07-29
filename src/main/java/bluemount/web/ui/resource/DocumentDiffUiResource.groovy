package bluemount.web.ui.resource

import org.restlet.representation.Representation
import org.restlet.resource.Get
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import javax.inject.Inject

@Scope("prototype")
@Component
class DocumentDiffUiResource extends UiResource {

  @Inject DocumentDiffUiResource() {
  }

  @Get("html")
  Representation list() {
    freemarker("packages/diff")
  }

}
