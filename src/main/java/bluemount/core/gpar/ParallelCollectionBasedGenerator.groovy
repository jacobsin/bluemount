package bluemount.core.gpar

import bluemount.common.utils.Timing
import groovyx.gpars.GParsPool


@Mixin(Timing)
class ParallelCollectionBasedGenerator implements ApprovalDocumentsGenerator {

  ApprovalDocumentsGeneratorImpl decorated

  ParallelCollectionBasedGenerator(ApprovalDocumentsGeneratorImpl decorated) {
    this.decorated = decorated
  }

  void buildPairs(List<DocumentPair> pairs) {

    GParsPool.withPool(40) {pool->
      pairs.eachParallel {pair->
        GParsPool.withExistingPool(pool) {
          def docs = ["dev", "prod"].collectParallel {decorated.generate(pair.documentType, pair.permutation, it)}

          pair.before = docs[0]
          pair.after = docs[1]
        }

        pair.diffResult = decorated.diff(pair)
      }
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
