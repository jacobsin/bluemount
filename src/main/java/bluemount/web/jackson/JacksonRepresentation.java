package bluemount.web.jackson;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;

public class JacksonRepresentation<T> extends org.restlet.ext.jackson.JacksonRepresentation<T> {
    public JacksonRepresentation(MediaType mediaType, T object) {
        super(mediaType, object);
    }

    public JacksonRepresentation(Representation representation, Class<T> objectClass) {
        super(representation, objectClass);
    }

    public JacksonRepresentation(T object) {
        super(object);
    }

    @Override
    protected ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = super.createObjectMapper();
        configure(objectMapper);
        return objectMapper;
    }

    private void configure(ObjectMapper objectMapper) {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE, JsonTypeInfo.As.PROPERTY);
    }
}