package bluemount.core.gpar.actors

import bluemount.core.gpar.ApprovalDocumentsGeneratorImpl
import bluemount.core.gpar.DocumentPair
import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

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
