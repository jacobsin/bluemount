package bluemount.core.gpar.actors

import bluemount.common.utils.Timing
import bluemount.core.gpar.ApprovalDocumentsGenerator
import bluemount.core.gpar.ApprovalDocumentsGeneratorImpl
import bluemount.core.gpar.DocumentPair
import bluemount.core.gpar.DocumentSet

@Mixin(Timing)
class ActorsBasedGenerator implements ApprovalDocumentsGenerator {

  ApprovalDocumentsGeneratorImpl decorated

  ActorsBasedGenerator(ApprovalDocumentsGeneratorImpl decorated) {
    this.decorated = decorated
  }

  void buildPairs(List<DocumentPair> pairs) {
    def master = new Master(pairs: pairs, decorated: decorated, numWorkers: 20).start()
    master.waitUntilDone()
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
