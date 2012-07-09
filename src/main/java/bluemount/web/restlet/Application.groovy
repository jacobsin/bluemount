package bluemount.web.restlet

import freemarker.cache.NullCacheStorage
import freemarker.template.Configuration
import freemarker.template.ObjectWrapper
import org.restlet.Context
import org.restlet.ext.freemarker.ContextTemplateLoader

class Application extends org.restlet.Application {
  Configuration configuration

  Application(Context context) {
    super(context)
    configuration = new Configuration()
    configuration.templateLoader = new ContextTemplateLoader(context, "war:///WEB-INF/templates")
    configuration.objectWrapper = ObjectWrapper.BEANS_WRAPPER
    configuration.cacheStorage = new NullCacheStorage() //TODO might wanna do this only for dev
  }
}
