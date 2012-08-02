package bluemount.common.permission

import org.junit.Before
import org.junit.Test

import static bluemount.common.permission.Action.manage
import static bluemount.common.permission.MyAction.*
import static bluemount.common.permission.Role.*

class AbilityTest {

  Map<String, Ability> abilities = [:]

  @Before
  def void before() {
    setupAbilities()
  }

  @Test
  def void canRuleOnClass() {
    assert ability(Standard).ableTo(read, Public)
  }

  @Test
  def void canRuleOnInstance() {
    assert ability(Standard).ableTo(read, new Public())
  }

  @Test
  def void missingRuleOnClass() {
    assert ability(Standard).notAbleTo(delete, User)
  }

  @Test
  def void missingRuleOnInstance() {
    assert ability(Standard).notAbleTo(delete, new User())
  }

  @Test
  def void cannotRuleOnClass() {
    assert ability(Standard).notAbleTo(update, History)
  }

  @Test
  def void cannotRuleOnInstance() {
    assert ability(Standard).notAbleTo(update, new History())
  }

  @Test
  def void laterCanRuleAddPermission() {
    assert ability(Standard).ableTo(read, Public)
    assert ability(Standard).ableTo(read, History)
  }

  @Test
  def void laterCanRuleOverridesEarlierCannotRule() {
    assert ability(Standard).notAbleTo(update, User)
    assert ability(Admin).ableTo(update, User)
  }

  @Test
  def void laterCannotRuleOverridesEarlierCanRule() {
    assert ability(Standard).ableTo(read, History)
    assert ability(Guest).notAbleTo(read, History)
  }

  @Test
  def void canAllRuleMatchesAnyClass() {
    assert ability(Standard).ableTo(update, Public)
    assert ability(Standard).ableTo(update, Confidential)
  }

  @Test
  def void canAllRuleOnMatchesInstancesOfAnyClass() {
    assert ability(Standard).ableTo(update, new Public())
    assert ability(Standard).ableTo(update, new Confidential())
  }

  @Test
  def void canManageRuleMatchesAnyAction() {
    assert ability(Admin).ableTo(read, Public)
    assert ability(Admin).ableTo(create, Public)
    assert ability(Admin).ableTo(update, Public)
    assert ability(Admin).ableTo(delete, Public)
    assert ability(Admin).ableTo(disable, Public)
  }

  def ability(role) {
    abilities[role.name()]
  }

  void setupAbilities() {
    abilities << Role.values().collectEntries {[(it.name()): new MyAbility(new User(role: it))]}
  }
}

class Confidential {}
class Public {}
class History {}

enum Role {
  Admin, Standard, Readonly, Guest
}

class User {
  Role role
}

enum MyAction implements Action {
  read, create, update, delete, disable
}

@Mixin(Ability)
class MyAbility {
  MyAbility(user) {
    can read, Public
    can read, History
    can read, Confidential
    can update, All
    cannot update, History
    cannot update, User

    switch(user.role) {
      case Standard:
        break

      case Admin:
        can manage, Public
        can update, User
        break

      case Readonly:
        break

      case Guest:
      default:
        cannot read, History
        break
    }
  }

}
