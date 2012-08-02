package bluemount.common.permission


class Ability {
  List<Rule> rules = []

  void can(actions, Class subject) {
    rules << new Rule(baseBehaviour: true, actions: actions, subject: subject)
  }

  void cannot(actions, Class subject) {
    rules << new Rule(baseBehaviour: false, actions: actions, subject: subject)
  }

  boolean ableTo(Action action, subject) {
    rules.reverse().find {it.matchesConditions(action, subject)}?.baseBehaviour
  }

  boolean notAbleTo(Action action, subject) {
    !ableTo(action, subject)
  }
}

