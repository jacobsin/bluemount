package bluemount.web.restlet.jackson

import org.restlet.data.MediaType
import org.restlet.representation.Representation

class JacksonConverter extends org.restlet.ext.jackson.JacksonConverter {
    @Override
    protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
        new JacksonRepresentation<T>(mediaType, source)
    }

    @Override
    protected <T> JacksonRepresentation<T> create(Representation source, Class<T> objectClass) {
        new JacksonRepresentation<T>(source, objectClass)
    }
}
