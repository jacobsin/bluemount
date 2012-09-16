package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.util.TextPosition

@Mixin(PdfLoading)
class PdfUpdater {
  def append(PDDocument document, String fullPath, TextPosition position, String text) {
    def page1 = document.documentCatalog.allPages[0] as PDPage

    PDPageContentStream contentStream = new PDPageContentStream(document, page1, true, true, true)

    contentStream.with {
      beginText()
      setFont( getFont(position.font), position.fontSize )
      moveTextPositionByAmount( position.endX, position.endY )
      drawString( text )
      endText()
      close()
    }

    document.save(fullPath)
  }

  PDFont getFont(PDFont pdFont) {
    PDType1Font.TIMES_ROMAN
  }
}
