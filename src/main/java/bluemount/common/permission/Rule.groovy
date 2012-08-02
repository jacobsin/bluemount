package bluemount.common.permission

import static bluemount.common.permission.Action.manage

class Rule {
  boolean baseBehaviour
  private List<Action> actions = []
  Class subject

  boolean matchesConditions(Action action, subject) {
    matchesAction(action) && matchesSubject(subject)
  }

  private boolean matchesSubject(subject) {
    (this.subject == All || this.subject == subject || subject.class.isAssignableFrom(this.subject))
  }

  private boolean matchesAction(Action action) {
    (this.actions.contains(manage) || this.actions.contains(action))
  }

  void setActions(actions) {
    this.actions = actions instanceof List ? actions : [actions]
  }
}

class All {}