package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument



@Category(Object)
class PDDocumentLoading {
  def <V> V loadDocument(String path, Closure<V> closure) {
    def fullPath = "${this.class.package.name.replaceAll("\\.", "/")}/${path}"
    def document = PDDocument.load(this.class.classLoader.getResource(fullPath))
    use(PDDocumentResource) {
      document.use closure.rcurry(fullPath)
    }
  }
}
