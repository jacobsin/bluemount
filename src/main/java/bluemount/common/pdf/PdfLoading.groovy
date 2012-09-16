package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument



@Category(Object)
class PdfLoading {
  def <V> V loadDocument(String path, Closure<V> closure) {
    def fullPath = "${this.class.package.name.replaceAll("\\.", "/")}/${path}"
    def resource = this.class.classLoader.getResource(fullPath)
    if (!resource) throw new FileNotFoundException(fullPath)
    def document = PDDocument.load(resource)
    use(PdfResource) {
      document.using closure.rcurry(fullPath)
    }
  }
}
