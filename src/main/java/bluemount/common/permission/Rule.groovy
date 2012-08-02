package bluemount.common.permission

import static bluemount.common.permission.Action.manage

class Rule {
  boolean baseBehaviour
  private List<Action> actions = []
  private List<Class> subjects

  boolean matchesConditions(Action action, subject) {
    matchesAction(action) && matchesSubject(subject)
  }

  private boolean matchesSubject(subject) {
    (this.subjects.contains(All) || this.subjects.contains(subject) || this.subjects.any{subject.class.isAssignableFrom(it)})
  }

  private boolean matchesAction(Action action) {
    (this.actions.contains(manage) || this.actions.contains(action))
  }

  void setActions(actions) {
    this.actions = actions instanceof Collection ? actions.toList() : [actions]
  }

  void setSubjects(subjects) {
    this.subjects = subjects instanceof Collection ? subjects.toList() : [subjects]
  }
}

class All {}