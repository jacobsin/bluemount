package bluemount.web.restlet.jackson

import org.codehaus.jackson.map.ObjectMapper
import org.restlet.data.MediaType
import org.restlet.representation.Representation

class JacksonRepresentation<T> extends org.restlet.ext.jackson.JacksonRepresentation<T> {
  JacksonRepresentation(MediaType mediaType, T object) {
    super(mediaType, object)
  }

  JacksonRepresentation(Representation representation, Class<T> objectClass) {
    super(representation, objectClass)
  }

  JacksonRepresentation(T object) {
    super(object)
  }

  @Override
  protected ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = super.createObjectMapper()
    configure(objectMapper)
    objectMapper
  }

  private void configure(ObjectMapper objectMapper) {
  }

  public <O> O getObject(Class<O> objectClass) {
    setObjectClass((Class<T>) objectClass)
    (O) getObject()
  }
}
