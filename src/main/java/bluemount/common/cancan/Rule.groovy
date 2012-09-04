package bluemount.common.cancan

import groovy.transform.ToString

@ToString
class Rule {
  boolean baseBehaviour
  List<Action> actions = []
  List<Class> subjects
  private Closure block

  boolean matchesConditions(Action action, subject) {
    matchesAction(action) && matchesSubject(subject) &&
        (!block || !(subject instanceof Class) && block.call(subject))
  }

  private boolean matchesSubject(subject) {
    (this.subjects.contains(All) || this.subjects.contains(subject) || this.subjects.any{subject.class.isAssignableFrom(it)})
  }

  private boolean matchesAction(Action action) {
    (this.actions.contains(Action.manage) || this.actions.contains(action))
  }

  void setActions(actions) {
    this.actions = actions instanceof Collection ? actions.toList() : [actions]
  }

  void setSubjects(subjects) {
    this.subjects = subjects instanceof Collection ? subjects.toList() : [subjects]
  }

}

class All {}