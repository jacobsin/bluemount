package bluemount.web.restlet;

import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RestletUtils {

    private static final Logger log = LoggerFactory.getLogger(RestletUtils.class);

    public static void replaceConverter(Class<? extends ConverterHelper> converterClass, ConverterHelper newConverter) {

        ConverterHelper oldConverter = null;

        List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
        for (int i = 0; i < converters.size(); i++) {
            ConverterHelper converter = converters.get(i);
            if (converter.getClass().equals(converterClass)) {
                oldConverter = converters.set(i, newConverter);
                break;
            }
        }

        if (oldConverter == null) {
            log.debug("Added {} to Restlet Engine", newConverter.getClass());
        } else {
            log.debug("Replaced {} with {} in Restlet Engine", oldConverter.getClass(), newConverter.getClass());
        }
    }
}
