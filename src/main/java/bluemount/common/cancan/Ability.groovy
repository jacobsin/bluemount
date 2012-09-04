package bluemount.common.cancan

class Ability {
  List<Rule> rules = []

  void can(actions, subjects, Closure block = null) {
    rules << new Rule(baseBehaviour: true, actions: actions, subjects: subjects, block: block)
  }

  void cannot(actions, subjects, Closure block = null) {
    rules << new Rule(baseBehaviour: false, actions: actions, subjects: subjects, block: block)
  }

  boolean ableTo(Action action, subject) {
    findRule(action, subject)?.baseBehaviour
  }

  def findRule(Action action, subject) {
    rules.reverse().find {it.matchesConditions(action, subject)}
  }

  boolean notAbleTo(Action action, subject) {
    !ableTo(action, subject)
  }

  def authorize(Action action, subject){
    if (!ableTo(action, subject)){
      throw new AccessDeniedException(action, subject)
    }
    subject
  }

  Collection<Action> all(subject) {
    if (!rules) return Collections.emptySet()
    Action action = rules.find {it.actions != [Action.manage]}.actions.find {it != Action.manage}
    if (!action.respondsTo('values')) throw new IllegalStateException("Don't know how to list ${action.class.simpleName} values")

    action.values().findAll { ableTo(it, subject) }.toSet()
  }
}

