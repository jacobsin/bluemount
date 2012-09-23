package bluemount.core.gpar

import bluemount.common.utils.Timing
import groovyx.gpars.GParsExecutorsPool
import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

import java.util.concurrent.CountDownLatch

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

class Generator extends DefaultActor {
  ApprovalDocumentsGeneratorImpl decorated

  void act() {
    loop {
      react {msg->
        switch(msg) {
          case GenerateRequest:
            def request = msg as GenerateRequest
            def pair = request.pair
            pair[request.side] = decorated.generate(pair.documentType, pair.permutation, request.env)
            reply pair
        }
      }
    }
  }
}

class Differ extends DefaultActor {
  ApprovalDocumentsGeneratorImpl decorated

  void act() {
    loop {
      react {msg->
        switch(msg) {
          case DocumentPair:
            def pair = msg as DocumentPair
            pair.diffResult =  decorated.diff(msg)
            reply pair
        }
      }
    }
  }
}

class PairBuilder extends DefaultActor {
  ApprovalDocumentsGeneratorImpl decorated
  Actor generator1
  Actor generator2
  Actor differ
  Actor master

  void beginWorks() {
    createWorkers()
  }

  void createWorkers() {
    generator1 = new Generator(decorated: decorated).start()
    generator2 = new Generator(decorated: decorated).start()
    differ = new Differ(decorated: decorated).start()
  }

  void act() {
    beginWorks()
    loop {
      react {msg->
        switch (msg) {
          case DocumentPair:
            def pair = msg as DocumentPair
            if (!pair.before) generator1 << new GenerateRequest(pair: pair, side: "before", env: "prod")
            if (pair.before && !pair.after) generator2 << new GenerateRequest(pair: pair, side: "after", env: "dev")
            if (pair.before && pair.after && !pair.diffSet) differ << pair
            if (pair.diffSet) master <<  pair
        }
      }
    }
  }
}

class Master extends DefaultActor {
  ApprovalDocumentsGeneratorImpl decorated
  List<DocumentPair> pairs

  int numWorkers = 1

  private CountDownLatch startupLatch = new CountDownLatch(1)
  private CountDownLatch doneLatch

  void beginGenerating() {
    int count = sendTasksToWorkers()
    doneLatch = new CountDownLatch(count)
  }

  int sendTasksToWorkers() {
    List<Actor> workers = createWorkers()
    int count = 0
    pairs.each{pair->
      workers[count % numWorkers] << pair
      count ++
    }
    count
  }

  List<Actor> createWorkers() {
    (1..numWorkers).collect {new PairBuilder(decorated: decorated, master: this).start()}
  }

  void waitUntilDone() {
    startupLatch.await()
    doneLatch.await()
  }

  void act() {
    beginGenerating()
    startupLatch.countDown()
    loop {
      react {msg->
        switch(msg) {
          case DocumentPair:
            doneLatch.countDown()
        }
      }
    }
  }
}

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

class GenerateRequest {
  DocumentPair pair
  String side
  String env
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