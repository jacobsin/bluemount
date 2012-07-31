package bluemount.common.permission

import org.junit.Before
import org.junit.Test

import static bluemount.common.permission.MyAction.read

class AbilityTest {

  def users = [:]

  @Before
  def void before() {
    setupUsers()
  }

  @Test
  def void canRuleOnClass() {
    assert ability(Role.Guest).ableTo(read, Public)
  }

  @Test
  def void canRuleOnInstance() {
    assert ability(Role.Guest).ableTo(read, new Public())
  }

  @Test
  def void cannotRuleOnClass() {
    assert !ability(Role.Guest).ableTo(read, Confidential)
  }

  def ability(role) {
    new MyAbility(users[role.name()])
  }

  void setupUsers() {
    users << Role.values().collectEntries {[(it.name()): new User(role: it)]}
  }
}

class Confidential {}
class Public {}

enum Role {
  Admin, Standard, Readonly, Guest
}

class User {
  Role role
}

enum MyAction implements Action {
  read, create, update, delete
}

@Mixin(Ability)
class MyAbility {
  MyAbility(user) {
    can read, Public

    switch(user.role) {
      case Role.Readonly:
        break

      case Role.Standard:
        break

      case Role.Admin:
        break

      case Role.Guest:
      default:
        break
    }
  }

  List<Rule> rules = []

  void can(Action action, Class subject) {
    rules << new Rule(baseBehaviour: true, action: action, subject: subject)
  }

  boolean ableTo(Action action, subject) {
    rules.find {it.matchesConditions(action, subject)}?.baseBehaviour
  }


}
