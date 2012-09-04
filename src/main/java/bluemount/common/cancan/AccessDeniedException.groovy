package bluemount.common.cancan

import groovy.transform.InheritConstructors

@InheritConstructors
class AccessDeniedException extends RuntimeException {

  AccessDeniedException(Action action, subject) {
    super("${action.toString().capitalize()} on ${humanize(subject)} unauthorized")
  }

  private static def humanize(subject) {
    def clazz = subject instanceof Class ? subject : subject.class
    clazz.simpleName
  }
}
