package bluemount.core.gpar

import bluemount.common.utils.Timing
import groovyx.gpars.GParsExecutorsPool

import static groovyx.gpars.GParsPool.withExistingPool
import static groovyx.gpars.GParsPool.withPool

interface ApprovalDocumentsGenerator {
  DocumentSet generateDocuments()
}

@Mixin(Timing)
class ApprovalDocumentsGeneratorImpl implements ApprovalDocumentsGenerator {
  DocumentGenerator documentGenerator
  DocumentDiffer documentDiffer
  List<String> allPermutations = (1..12).collect {"permutation${it}"}
  List<String> allDocumentTypes = (1..6).collect {"documentType${it}"}

  ApprovalDocumentsGeneratorImpl(DocumentGenerator documentGenerator, DocumentDiffer documentDiffer) {
    this.documentGenerator = documentGenerator
    this.documentDiffer = documentDiffer
  }

  DocumentSet generateDocuments() {
    timed("total %s ms") {
      def pairs = initPairs()
      buildPairs(pairs)
      new DocumentSet(documentPairs: pairs)
    }
  }

  def void buildPairs(List<DocumentPair> pairs) {
    pairs.each this.&buildPair
  }

  def void buildPair(DocumentPair pair) {
    pair.before = generate(pair.documentType, pair.permutation, "prod")
    pair.after = generate(pair.documentType, pair.permutation, "dev")
    pair.diffResult = diff(pair)
  }

  def List<DocumentPair> initPairs() {
    allPermutations.collectMany { permutation ->
      allDocumentTypes.collect { documentType ->
        new DocumentPair(permutation: permutation, documentType: documentType)
      }
    } as List<DocumentPair>
  }

  def generate(String documentType, String permutation, String env) {
    documentGenerator.generate(permutation, documentType, "prod")
  }

  def diff(DocumentPair pair) {
    documentDiffer.diff(pair)
  }
}

@Mixin(Timing)
class ParallelCollectionBasedGenerator implements ApprovalDocumentsGenerator {

  ApprovalDocumentsGeneratorImpl decorated

  ParallelCollectionBasedGenerator(ApprovalDocumentsGeneratorImpl decorated) {
    this.decorated = decorated
  }

  void buildPairs(List<DocumentPair> pairs) {

    withPool(40) {pool->
      pairs.eachParallel {pair->
        withExistingPool(pool) {
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

//@ActiveObject
//@Mixin(Timing)
//class ActorsBasedGenerator implements ApprovalDocumentsGenerator {
//
//  ApprovalDocumentsGeneratorImpl decorated
//
//  ActorsBasedGenerator(ApprovalDocumentsGeneratorImpl decorated) {
//    this.decorated = decorated
//  }
//
//  void buildPairs(List<DocumentPair> pairs) {
//    def built = []
//    pairs.each {pair->
//      built << this.buildPair(pair)
//    }
//    built*.get()
//  }
//
//  @ActiveMethod
//  def buildPair(DocumentPair pair) {
//    pair.before = generate(pair.documentType, pair.permutation, "prod").get()
//    pair.after = generate(pair.documentType, pair.permutation, "dev").get()
//    pair.diffResult = diff(pair).get()
//    pair
//  }
//
//  @ActiveMethod
//  def generate(String documentType, String permutation, String env) {
//    decorated.generate(documentType, permutation, env)
//  }
//
//  @ActiveMethod
//  def diff(DocumentPair pair) {
//    decorated.diff(pair)
//  }
//
//  @Override
//  DocumentSet generateDocuments() {
//    timed {
//      def pairs = decorated.initPairs()
//      buildPairs(pairs)
//      new DocumentSet(documentPairs: pairs)
//    }
//  }
//}



@Mixin(Timing)
class DocumentGenerator {
  Document generate(String permutation, String documentType, String env) {
    timed("generate %s ms - $permutation, $documentType, $env") {
      sleep 2500
      return new Document(permutation: permutation, documentType: documentType, env: env)
    }
  }
}

@Mixin(Timing)
class DocumentDiffer {
  DiffResult diff(DocumentPair pair) {
    timed("diff %s ms - $pair.permutation, $pair.documentType") {
      return new DiffResult(diffSet: new DiffSet(), highlighted: new Document())
    }
  }
}

class DiffResult {
  DiffSet diffSet
  Document highlighted
}

class DocumentPair {
  String permutation
  String documentType
  Document before
  Document after
  Document highlighted
  DiffSet diffSet

  void setDiffResult(DiffResult result) {
    highlighted = result.highlighted
    diffSet = result.diffSet
  }
}

class Document {
  String permutation
  String documentType
  String env
}

class DiffSet {

}

class DocumentSet {
  List<DocumentPair> documentPairs
}