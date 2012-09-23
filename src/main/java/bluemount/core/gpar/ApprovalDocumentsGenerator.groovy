package bluemount.core.gpar

import bluemount.common.utils.Timing


@Mixin(Timing)
class ApprovalDocumentsGenerator {
  DocumentGenerator documentGenerator
  DocumentDiffer documentDiffer
  List<String> allPermutations = (1..12).collect {"permutation${it}"}
  List<String> allDocumentTypes = (1..6).collect {"documentType${it}"}

  ApprovalDocumentsGenerator(DocumentGenerator documentGenerator, DocumentDiffer documentDiffer) {
    this.documentGenerator = documentGenerator
    this.documentDiffer = documentDiffer
  }

  DocumentSet generateDocuments() {
    timed("total %s ms") {
      def pairs = allPermutations.collectMany { permutation ->
        allDocumentTypes.collect { documentType ->
          def before = documentGenerator.generate(permutation, documentType, "prod")
          def after = documentGenerator.generate(permutation, documentType, "dev")

          def pair = new DocumentPair(permutation: permutation, documentType: documentType, before: before, after: after)

          pair.diffResult = documentDiffer.diff(pair)

          pair
        }
      }

      new DocumentSet(documentPairs: pairs)
    }
  }
}

@Mixin(Timing)
class DocumentGenerator {
  Document generate(String permutation, String documentType, String env) {
    timed("generate %s ms - $permutation, $documentType, $env") {
//      sleep 2500
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