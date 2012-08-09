package bluemount.web.api

import bluemount.web.restlet.jackson.JacksonRepresentation
import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.restlet.data.MediaType
import org.restlet.data.Status
import org.restlet.ext.fileupload.RestletFileUpload
import org.restlet.representation.Representation
import org.restlet.resource.Post
import org.restlet.resource.ServerResource

class FileUploadApiResource extends ServerResource {


  @Post
  def save(Representation entity) {
    if (!entity) {
      response.status = Status.CLIENT_ERROR_BAD_REQUEST
      return
    }

    def uploaded = []

    if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
      def storeDirectory = new File("/tmp/")

      def factory = new DiskFileItemFactory()
      factory.sizeThreshold = 1000240

      def upload = new RestletFileUpload(factory)
      def items = upload.parseRequest(request)

      items.each {
        if (it.name) {
          it.write(new File(storeDirectory, it.name))
          uploaded << [name: it.name, contentType: it.contentType, size: it.size, fieldName: it.fieldName]
        }
      }
    }

    new JacksonRepresentation(uploaded)
  }
}
