package bluemount.common.permission


class Ability {
  List<Rule> rules = []

  void can(actions, subjects) {
    rules << new Rule(baseBehaviour: true, actions: actions, subjects: subjects)
  }

  void cannot(actions, subjects) {
    rules << new Rule(baseBehaviour: false, actions: actions, subjects: subjects)
  }

  boolean ableTo(Action action, subject) {
    rules.reverse().find {it.matchesConditions(action, subject)}?.baseBehaviour
  }

  boolean notAbleTo(Action action, subject) {
    !ableTo(action, subject)
  }
}

