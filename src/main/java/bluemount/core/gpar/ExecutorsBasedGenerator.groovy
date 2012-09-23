package bluemount.core.gpar

import bluemount.common.utils.Timing
import groovyx.gpars.GParsExecutorsPool


@Mixin(Timing)
class ExecutorsBasedGenerator implements ApprovalDocumentsGenerator {

  ApprovalDocumentsGeneratorImpl decorated

  ExecutorsBasedGenerator(ApprovalDocumentsGeneratorImpl decorated) {
    this.decorated = decorated
  }

  void buildPairs(List<DocumentPair> pairs) {
    GParsExecutorsPool.withPool(40) {
      pairs.eachParallel decorated.&buildPair
    }
  }

  @Override
  DocumentSet generateDocuments() {
    timed {
      def pairs = decorated.initPairs()
      buildPairs(pairs)
      new DocumentSet(documentPairs: pairs)
    }
  }
}
