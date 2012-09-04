package bluemount.common.cancan

import org.junit.Test

import static bluemount.common.cancan.MyAction.*
import static bluemount.common.cancan.Action.*

class AbilityTest {

  def ability = new Ability()
  def shouldFail = new GroovyTestCase().&shouldFail

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

  @Test
  def void canRuleWithMultipleSubjects() {
    ability.can(read, [Public, Confidential])

    assert ability.ableTo(read, Public)
    assert ability.ableTo(read, Confidential)
  }

  @Test
  def void cannotRuleWithMultipleSubjects() {
    ability.cannot(read, [Public, Confidential])

    assert !ability.ableTo(read, Public)
    assert !ability.ableTo(read, Confidential)
  }

  @Test
  def void canRuleWithClosure() {
    ability.can(read, Public) {
      it.readable
    }

    assert ability.ableTo(read, new Public(readable: true))
    assert !ability.ableTo(read, new Public(readable: false))
  }

  @Test
  def void canRuleWithClosureReturnFalseForClass() {
    ability.can(read, Public) {
      it.readable
    }

    assert !ability.ableTo(read, Public)
  }

  @Test
  def void cannotRuleWithClosure() {
    ability.can(update, Public)
    ability.cannot(update, Public) {
      it.readonly
    }

    assert !ability.ableTo(update, new Public(readonly: true))
    assert ability.ableTo(update, new Public(readonly: false))
  }

  @Test
  def void allOnEmptyAbility() {
    assert ability.all(Public) == [].toSet()
  }

  @Test
  def void allOnAbilityWithNonEnumAction() {
    ability.can(NonEnumAction.action1, Public)
    assert ability.all(Public) == [NonEnumAction.action1].toSet()
  }

  @Test
  def void allOnAbilityWithNonEnumActionWithNoValuesMethod() {
    ability.can(NonEnumActionWithoutValuesMethod.action1, Public)
    assert shouldFail {ability.all(Public)} == "Don't know how to list NonEnumActionWithoutValuesMethod values"
  }

  @Test
  def void allWithSingleCan() {
    ability.can update, Public

    assert ability.all(Public) == [update].toSet()
  }

  @Test
  def void allWithMultipleCan() {
    ability.can([update, read], Public)
    ability.can read, Public

    assert ability.all(Public) == [update, read].toSet()
  }

  @Test
  def void allWithMultipleCanOnDifferentSubject() {
    ability.can delete, Confidential
    ability.can ([read, delete], Public)

    assert ability.all(Public) == [read, delete].toSet()
    assert ability.all(Confidential) == [delete].toSet()
  }

  @Test
  def void allWithSingleCannot() {
    ability.rules =[]
    ability.cannot update, Public

    assert ability.all(Public) == [].toSet()
  }

  @Test
  def void allWithMixedCanCannot() {
    ability.can update, Public
    ability.cannot update, Public

    assert ability.all(Public) == [].toSet()
  }

  @Test
  def void allWithCanManageRule() {
    ability.can manage, Public
    ability.can ([manage, read], Confidential)

    assert ability.all(Public) == [update, disable, delete, create, read].toSet()
  }

  @Test
  def void allWithMixedCanManageAndCannotRules() {
    ability.can manage, Public
    ability.cannot delete, Public

    assert ability.all(Public) == [update, disable, create, read].toSet()
  }

  @Test
  def void allWithAllSubjectRule() {
    ability.can read, All

    assert ability.all(Public) == [read].toSet()
    assert ability.all(Confidential) == [read].toSet()
  }

  @Test
  def void allWithClosureCan() {
    ability.can(read, Public) {
      it.readable
    }

    assert ability.all(new Public(readable: true)) == [read].toSet()
    assert ability.all(new Public(readable: false)) == [].toSet()
  }

  @Test
  def void allWithClosureCanOnClassSubject() {
    ability.can(read, Public) {
      it.readable
    }

    assert ability.all(Public) == [].toSet()
  }

  @Test
  def void authorizePass() {
    ability.can(read, Public)
    assert ability.authorize(read,Public) == Public
  }

  @Test
  def void authorizeFail() {
    assert shouldFail(AccessDeniedException){ability.authorize(read,Public)} == "Read on Public unauthorized"
  }

  @Test
  def void authorizeFailOnInstance() {
    assert shouldFail(AccessDeniedException){ability.authorize(read,new Public())} == "Read on Public unauthorized"
  }
}

class NonEnumAction implements Action {
  static NonEnumAction action1 = new NonEnumAction()
  static NonEnumAction action2 = new NonEnumAction()

  def static values() {
    [action1, action2]
  }
}

class NonEnumActionWithoutValuesMethod implements Action {
  static NonEnumActionWithoutValuesMethod action1 = new NonEnumActionWithoutValuesMethod()
}