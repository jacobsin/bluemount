package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage



@Mixin(PDDocumentLoading)
class PdfUpdater {
  def update(String path) {
    loadDocument(path) {PDDocument document, String fullPath->
      def page1 = document.documentCatalog.allPages[0] as PDPage
//      def contents = page1.resources
      document.save("./build/test-classes/${fullPath}".replaceAll(/\.pdf/, ".update.pdf"))
    }
  }
}
