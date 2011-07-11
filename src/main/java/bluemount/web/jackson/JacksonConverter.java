package bluemount.web.jackson;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;

public class JacksonConverter extends org.restlet.ext.jackson.JacksonConverter {
    @Override
    protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
        return new JacksonRepresentation<T>(mediaType, source);
    }

    @Override
    protected <T> JacksonRepresentation<T> create(Representation source, Class<T> objectClass) {
        return new JacksonRepresentation<T>(source, objectClass);
    }
}
