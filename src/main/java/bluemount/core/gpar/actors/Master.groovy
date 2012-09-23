package bluemount.core.gpar.actors

import bluemount.core.gpar.ApprovalDocumentsGeneratorImpl
import bluemount.core.gpar.DocumentPair
import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

import java.util.concurrent.CountDownLatch

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
