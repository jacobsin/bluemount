package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper



@Mixin(PDDocumentLoading)
class PdfTextExtractor {

  def String getText(String path) {
    loadDocument(path) {PDDocument document, String fullPath->
      new PDFTextStripper().getText(document)
    }
  }
}
