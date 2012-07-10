package bluemount.common.env


import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.devsurf.injection.guice.annotations.GuiceModule

@GuiceModule
class PropertiesLoadingModule extends AbstractModule {

  @Override
  protected void configure() {
    Names.bindProperties(binder(), properties)
  }

  private def getProperties() {
    def stream = this.class.getResourceAsStream("/app.properties")
    def properties = new Properties()
    try {
      properties.load(stream)
    } catch (IOException e) {
      binder().addError(e)
    }
    properties
  }
}
