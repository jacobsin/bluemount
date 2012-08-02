package bluemount.common.permission

import org.junit.Test

import static bluemount.common.permission.Action.manage
import static bluemount.common.permission.MyAction.read
import static bluemount.common.permission.MyAction.update

class AbilityTest {

  def ability = new Ability()

  @Test
  def void canRuleOnClass() {
    ability.can read, Public
    assert ability.ableTo(read, Public)
  }

  @Test
  def void canRuleOnInstance() {
    ability.can read, Public
    assert ability.ableTo(read, new Public())
  }

  @Test
  def void missingRuleOnClass() {
    assert !ability.ableTo(read, Public)
  }

  @Test
  def void missingRuleOnInstance() {
    assert !ability.ableTo(read, new Public())
  }

  @Test
  def void cannotRuleOnClass() {
    ability.cannot read, Public
    assert !ability.ableTo(read, Public)
  }

  @Test
  def void cannotRuleOnInstance() {
    ability.cannot read, Public
    assert !ability.ableTo(read, new Public())
  }

  @Test
  def void laterCanRuleAddPermission() {
    ability.can read, Public
    ability.can read, Confidential
    assert ability.ableTo(read, Public)
    assert ability.ableTo(read, Confidential)
  }

  @Test
  def void laterCanRuleOverridesEarlierCannotRule() {
    ability.cannot read, Public
    ability.can read, Public
    assert ability.ableTo(read, Public)
  }

  @Test
  def void laterCannotRuleOverridesEarlierCanRule() {
    ability.can read, Public
    ability.cannot read, Public
    assert !ability.ableTo(read, Public)
  }

  @Test
  def void canAllRuleMatchesAnyClass() {
    ability.can read, All
    assert ability.ableTo(read, Public)
    assert ability.ableTo(read, Confidential)
  }

  @Test
  def void canAllRuleOnMatchesInstancesOfAnyClass() {
    ability.can read, All
    assert ability.ableTo(read, new Public())
    assert ability.ableTo(read, new Confidential())
  }

  @Test
  def void canManageRuleMatchesAnyAction() {
    ability.can manage, Public
    MyAction.values().each {
      assert ability.ableTo(it, Public)
    }
  }

  @Test
  def void canRuleWithMultipleActions() {
    ability.can([read, update], Public)

    assert ability.ableTo(read, Public)
    assert ability.ableTo(update, Public)
  }

  @Test
  def void cannotRuleWithMultipleActions() {
    ability.cannot([read, update], Public)

    assert !ability.ableTo(read, Public)
    assert !ability.ableTo(update, Public)
  }
}

