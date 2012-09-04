package bluemount.web.restlet.filters

import org.restlet.Request
import org.restlet.Response
import org.restlet.data.MediaType
import org.restlet.representation.Representation
import org.restlet.routing.Filter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggingFilter extends Filter {

  static final Logger log = LoggerFactory.getLogger("bluemount.web.api")
  def excludedPaths = []
  def includedMediaTypes = [MediaType.APPLICATION_JSON]

  @Override
  protected int beforeHandle(Request request, Response response) {
    if (shouldInclude(request)) {
      def buf = new StringBuilder("Request:  ${request.method} ${request.resourceRef.path}")
      if (shouldInclude(request.entity)) buf << "\n${getEntity(request)}"
      log.debug(buf.toString())
    }
    CONTINUE
  }

  @Override
  protected void afterHandle(Request request, Response response) {
    if (shouldInclude(request)) {
      def buf = new StringBuilder("Response: ${request.method} ${request.resourceRef.path} ${response.status.code}")
      if (shouldInclude(response.entity)) buf << "\n${response.entityAsText}"
      log.debug(buf.toString())
    }
  }

  boolean shouldInclude(Request request) {
    !excludedPaths.any {request.resourceRef.path.matches(it as String)}
  }

  boolean shouldInclude(Representation entity) {
    entity.available && entity.mediaType in includedMediaTypes
  }

  String getEntity(Request request) {
    def entity = request.entityAsText
    request.setEntity(entity, request.entity.mediaType)
    entity
  }
}
