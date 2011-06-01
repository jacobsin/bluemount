package bluemount.common.env;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.devsurf.injection.guice.annotations.GuiceModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@GuiceModule
public class PropertiesLoadingModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), getProperties());
    }

    private Properties getProperties() {
        InputStream stream = getClass().getResourceAsStream("/app.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            binder().addError(e);
        }
        return properties;
    }
}
