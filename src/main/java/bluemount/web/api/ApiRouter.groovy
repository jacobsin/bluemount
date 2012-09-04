package bluemount.web.api

import bluemount.web.api.resource.ProjectsApiResource
import bluemount.web.restlet.guice.GuiceRouter
import com.google.inject.Injector
import org.restlet.Context

class ApiRouter extends GuiceRouter {
  ApiRouter(Injector injector, Context context) {
    super(injector, context)
  }

  @Override
  protected void attachRoutes() {
    attach("/projects/{projectType}", ProjectsApiResource)
    attach("/projects/", ProjectsApiResource)
    attach("/fileupload/", FileUploadApiResource)
  }
}
