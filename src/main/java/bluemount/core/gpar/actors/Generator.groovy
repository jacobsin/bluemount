package bluemount.core.gpar.actors

import bluemount.core.gpar.ApprovalDocumentsGeneratorImpl
import groovyx.gpars.actor.DefaultActor

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
