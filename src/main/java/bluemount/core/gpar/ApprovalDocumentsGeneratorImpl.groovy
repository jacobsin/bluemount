package bluemount.core.gpar

import bluemount.common.utils.Timing


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