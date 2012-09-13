package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument



@Category(PDDocument)
class PDDocumentResource {
  def <V> V use(Closure<V> closure) {
    try {
      closure(this)
    } finally {
      this.close()
    }
  }
}
