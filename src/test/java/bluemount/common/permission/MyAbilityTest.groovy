package bluemount.common.permission

import org.junit.Before
import org.junit.Test

import static bluemount.common.permission.MyAction.*
import static bluemount.common.permission.Role.*

class MyAbilityTest {
  Map<String, MyAbility> abilities = [:]

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
    MyAction.values().each {
      assert ability(Admin).ableTo(it, Public)
    }
  }

  @Test
  def void canRuleWithMultipleActions() {
    assert ability(Admin).ableTo(create, User)
    assert ability(Admin).ableTo(update, User)
  }

  @Test
  def void cannotRuleWithMultipleActions() {
    [create, update, delete, disable].each {
      assert !ability(Readonly).ableTo(it, Public)
    }
  }

  @Test
  def void canRuleWithMultipleSubjects() {
    [Confidential, History].each {
      assert ability(Standard).ableTo(read, it)
    }
  }

  @Test
  def void cannotRuleWithMultipleSubjects() {
    [User, History].each {
      assert !ability(Standard).ableTo(update, it)
    }
  }

  @Test
  def void canRuleWithClosure() {
    def ability = ability(Admin)
    assert ability.ableTo(delete, new Confidential(owner: ability.user))
    assert !ability.ableTo(delete, new Confidential(owner: new User()))
  }

  @Test
  def void cannotRuleWithClosure() {
    assert !ability(Standard).ableTo(update, new Public(readonly: true))
    assert ability(Standard).ableTo(update, new Public(readonly: false))
  }

  @Test
  def void cannotRuleWithClosureReturnFalseForClass() {
    assert !ability(Standard).ableTo(delete, Public)
  }

  def ability(role) {
    abilities[role.name()]
  }

  void setupAbilities() {
    abilities << Role.values().collectEntries {[(it.name()): new MyAbility(new User(role: it))]}
  }
}


