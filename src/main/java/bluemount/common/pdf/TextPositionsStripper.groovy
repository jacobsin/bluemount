package bluemount.common.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.util.PDFTextStripper
import org.apache.pdfbox.util.TextPosition
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TextPositionsStripper extends PDFTextStripper {
  static final Logger log = LoggerFactory.getLogger(TextPositionsStripper)
  def List<List<TextPosition>> textPositions = []

  TextPositionsStripper() throws IOException {
    super()
  }

  protected void processTextPosition(TextPosition text) {
    log.info("String[" + text.getXDirAdj() + "," +
        text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
        text.getXScale() + " height=" + text.getHeightDir() + " space=" +
        text.getWidthOfSpace() + " width=" +
        text.getWidthDirAdj() + "]" + text.getCharacter())
    super.processTextPosition(text)
  }


  List<List<TextPosition>> getTextPositions(PDDocument document) {
    getText(document)
    return textPositions
  }

  @Override
  protected void endPage(PDPage page) {
    textPositions << charactersByArticle.flatten()
  }
}
