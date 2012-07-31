package bluemount.common.permission


class Rule {
  boolean baseBehaviour
  Action action
  def subject

  boolean matchesConditions(Action action, subject) {
    matchesAction(action) && matchesSubject(subject)
  }

  private boolean matchesSubject(subject) {
    this.subject == subject
  }

  private boolean matchesAction(Action action) {
    this.action == action
  }
}
