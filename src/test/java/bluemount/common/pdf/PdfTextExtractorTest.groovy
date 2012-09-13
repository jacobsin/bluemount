package bluemount.common.pdf

import org.junit.Test

class PdfTextExtractorTest {

  @Test
  def void getText() {
    def extractor = new PdfTextExtractor()
    assert extractor.getText('test.pdf') == "This is a pdf text\nThis is line 2\n"
  }
}
