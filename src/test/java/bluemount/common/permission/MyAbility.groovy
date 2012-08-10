package bluemount.common.permission

import static bluemount.common.permission.Action.manage
import static bluemount.common.permission.MyAction.*
import static bluemount.common.permission.Role.*

@Mixin(Ability)
class MyAbility {
  def User user

  MyAbility(user) {
    this.user = user
    can read, Public
    can read, [History, Confidential]
    can update, All
    cannot([update,delete], Public) {
      it.readonly
    }
    cannot update, [History, User]

    switch(user.role) {
      case Standard:
        break

      case Admin:
        can manage, Public
        can manage, Confidential, {
          it.owner == user
        }
        can update, User
        can([create, update], User)
        break

      case Readonly:
        cannot([create, update, delete, disable], All)
        break

      case Guest:
      default:
        cannot([create, update, delete, disable], All)
        cannot read, History
        break
    }
  }

}

class Confidential {
  User owner
}
class Public {
  boolean readable
  boolean readonly
}
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