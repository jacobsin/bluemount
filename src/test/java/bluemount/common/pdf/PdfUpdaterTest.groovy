package bluemount.common.pdf

import org.junit.Test


class PdfUpdaterTest {

  @Test
  def void update() {
    new PdfUpdater().update('test.pdf')
    assert new PdfTextExtractor().getText('test.update.pdf')
  }
}
