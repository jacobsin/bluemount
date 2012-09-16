package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.junit.Test

@Mixin(PdfLoading)
class PdfUpdaterTest {

  PdfTextExtractor extractor = new PdfTextExtractor()

  @Test
  def void append() {
    loadDocument('test.pdf') {PDDocument document, String fullPath->
      def original = extractor.getText(document).trim()
      def positions = extractor.getTextPositions(document)
      def last = positions.last().last()

      new PdfUpdater().append(document, savePath(fullPath), last, 'moo')

      assert extractor.getText('test.append.pdf').trim() == original + 'moo'
    }
  }

  private String savePath(String fullPath) {
    "./build/test-classes/${fullPath}".replaceAll(/\.pdf/, '.append.pdf')
  }
}
