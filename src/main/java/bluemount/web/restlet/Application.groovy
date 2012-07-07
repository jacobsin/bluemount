package bluemount.web.restlet

import freemarker.template.Configuration
import org.restlet.Context
import org.restlet.ext.freemarker.ContextTemplateLoader

class Application extends org.restlet.Application {
  Configuration configuration

  Application(Context context) {
    super(context)
    configuration = new Configuration()
    configuration.templateLoader = new ContextTemplateLoader(context, "clap:///bluemount/web/templates")
  }
}
