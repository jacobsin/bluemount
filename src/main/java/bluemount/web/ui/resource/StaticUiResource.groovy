package bluemount.web.ui.resource

import org.restlet.representation.Representation
import org.restlet.resource.Get
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Scope("prototype")
@Component
class StaticUiResource extends UiResource {

  @Get
  Representation load() {
    freemarker("${requestAttributes.path}")
  }

}

