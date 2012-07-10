package bluemount.web.ui;

import bluemount.web.restlet.guice.GuiceRouter;
import bluemount.web.ui.resource.HomeUiResource;
import bluemount.web.ui.resource.ProjectsUiResource;
import com.google.inject.Injector;
import org.restlet.Context;

public class UiRouter extends GuiceRouter {
  public UiRouter(Injector injector, Context context) {
    super(injector, context);
  }

  @Override
  protected void attachRoutes() {
    attach("/projects.html", ProjectsUiResource.class);
    attach("/index.html", HomeUiResource.class);
  }
}
