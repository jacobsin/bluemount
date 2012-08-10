package bluemount.common.permission


class Ability {
  List<Rule> rules = []

  void can(actions, subjects, Closure block=null) {
    rules << new Rule(baseBehaviour: true, actions: actions, subjects: subjects, block: block)
  }

  void cannot(actions, subjects, Closure block=null) {
    rules << new Rule(baseBehaviour: false, actions: actions, subjects: subjects, block: block)
  }

  boolean ableTo(Action action, subject) {
    rules.reverse().find {it.matchesConditions(action, subject)}?.baseBehaviour
  }

  boolean notAbleTo(Action action, subject) {
    !ableTo(action, subject)
  }
}

