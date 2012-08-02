package bluemount.common.permission


class Ability {
  List<Rule> rules = []

  void can(Action action, Class subject) {
    rules << new Rule(baseBehaviour: true, action: action, subject: subject)
  }

  void cannot(Action action, Class subject) {
    rules << new Rule(baseBehaviour: false, action: action, subject: subject)
  }

  boolean ableTo(Action action, subject) {
    rules.reverse().find {it.matchesConditions(action, subject)}?.baseBehaviour
  }

  boolean notAbleTo(Action action, subject) {
    !ableTo(action, subject)
  }
}

