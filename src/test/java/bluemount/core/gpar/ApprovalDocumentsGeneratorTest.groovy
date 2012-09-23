package bluemount.core.gpar

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class ApprovalDocumentsGeneratorTest {
  def generator
  DocumentGenerator documentGenerator = new DocumentGenerator()
  DocumentDiffer documentDiffer = new DocumentDiffer()

  @Before
  def void before() {
    generator = new ApprovalDocumentsGeneratorImpl(documentGenerator, documentDiffer)
  }

  @Test @Ignore('Too slow')
  def void sequentialGenerate() {
    def documentSet = generator.generateDocuments()
    assertPairs(documentSet.documentPairs)
  }

  @Test
  def void parallelCollectionGenerate() {
    generator = new ParallelCollectionBasedGenerator(generator)
    def documentSet = generator.generateDocuments()
    assertPairs(documentSet.documentPairs)
  }

  @Test
  def void executorsGenerate() {
    generator = new ExecutorsBasedGenerator(generator)
    def documentSet = generator.generateDocuments()
    assertPairs(documentSet.documentPairs)
  }

//  @Test
//  def void actorsGenerate() {
//    generator = new ActorsBasedGenerator(generator)
//    def documentSet = generator.generateDocuments()
//    assertPairs(documentSet.documentPairs)
//  }

  private void assertPairs(List<DocumentPair> pairs) {
    assert pairs.size() == 72
    assert pairs.every {it.before}
    assert pairs.every {it.after}
    assert pairs.every {it.diffSet}
    assert pairs.every {it.highlighted}
  }
}
