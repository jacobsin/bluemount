package bluemount.common.permission

import static bluemount.common.permission.Action.manage

class Rule {
  boolean baseBehaviour
  Action action
  Class subject

  boolean matchesConditions(Action action, subject) {
    matchesAction(action) && matchesSubject(subject)
  }

  private boolean matchesSubject(subject) {
    (this.subject == All|| this.subject == subject || subject.class.isAssignableFrom(this.subject))
  }

  private boolean matchesAction(Action action) {
    (this.action == manage || this.action == action)
  }
}

class All {}