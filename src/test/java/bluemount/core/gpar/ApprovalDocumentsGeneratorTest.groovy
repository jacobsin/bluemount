package bluemount.core.gpar

import org.junit.Before
import org.junit.Test

class ApprovalDocumentsGeneratorTest {
  ApprovalDocumentsGenerator generator
  DocumentGenerator documentGenerator = new DocumentGenerator()
  DocumentDiffer documentDiffer = new DocumentDiffer()

  @Before
  def void before() {
    generator = new ApprovalDocumentsGenerator(documentGenerator, documentDiffer)
  }

  @Test
  def void sequentialGenerate() {
    def documentSet = generator.generateDocuments()
    def pairs = documentSet.documentPairs
    assert pairs.size() == 72
    assert pairs.every {it.before}
    assert pairs.every {it.after}
    assert pairs.every {it.diffSet}
    assert pairs.every {it.highlighted}
  }
}
