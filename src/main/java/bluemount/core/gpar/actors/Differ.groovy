package bluemount.core.gpar.actors

import bluemount.core.gpar.ApprovalDocumentsGeneratorImpl
import bluemount.core.gpar.DocumentPair
import groovyx.gpars.actor.DefaultActor

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
