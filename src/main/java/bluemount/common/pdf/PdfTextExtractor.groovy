package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

@Mixin(PdfLoading)
class PdfTextExtractor {

  def String getText(String path) {
    loadDocument(path) {PDDocument document, String fullPath->
      getText(document)
    }
  }

  def String getText(PDDocument document) {
    new PDFTextStripper().getText(document)
  }

  def getTextPositions(PDDocument document) {
    new TextPositionsStripper().getTextPositions(document)
  }
}
