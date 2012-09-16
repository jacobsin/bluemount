package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument



@Category(PDDocument)
class PdfResource {
  def <V> V using(Closure<V> closure) {
    try {
      closure(this)
    } finally {
      this.close()
    }
  }
}
