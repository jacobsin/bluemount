package bluemount.core.model

import org.junit.Test

class HashBuilderTest {
  @Test
  def void build() {
    def apple = new HashBuilder()
    def actual = apple {
      departments {
        management {
          employee {
            firstname "Steve"
            lastname "Jobs"
            title "CEO"
            fullname "$c.firstname $c.lastname"
          }
        }
      }
    }

    assert actual == [
        departments: [
            management: [
                employee: [
                    firstname: "Steve",
                    lastname: "Jobs",
                    title: "CEO",
                    fullname: "Steve Jobs"
                ]
            ]
        ]
    ]

    assert apple.toPrettyString() == '''{
    "departments": {
        "management": {
            "employee": {
                "firstname": "Steve",
                "lastname": "Jobs",
                "title": "CEO",
                "fullname": "Steve Jobs"
            }
        }
    }
}'''
  }

  @Test
  def void buildWithDefaults() {
    def apple = new AppleBuilder()
    assert apple() == [
        departments: [
            management: [
                employee: [
                    firstname: "Steve",
                    lastname: "Jobs",
                    title: "CEO",
                    fullname: "Steve Jobs"
                ]
            ]
        ]
    ]
  }

  @Test
  def void buildWithOverriddenDefaults() {
    def apple = new AppleBuilder()
    def employeeBuilder = new EmployeeBuilder()

    def actual = apple {
      departments {
        management {
          employee employeeBuilder {
            lastname "Sobs"
          }
        }
      }
    }

    assert actual == [
        departments: [
            management: [
                employee: [
                    firstname: "Steve",
                    lastname: "Sobs",
                    title: "CEO",
                    fullname: "Steve Sobs"
                ]
            ]
        ]
    ]
  }
}

class SampleHashBuilder extends HashBuilder {

  Closure defaults
  Closure deriveds

  SampleHashBuilder() {
    this.content = [:]
  }

  def call() {
    callClosures([defaults, deriveds])
  }

  @Override
  def call(Closure c) {
    callClosures([defaults, c, deriveds])
  }

  def callClosures(List<Closure> closures) {
    def map = content ? content.clone() : [:]
    closures.grep{it}.each{
      map << HashDelegate.cloneDelegateAndGetContent(it, map)
    }
    call(map)
  }
}

class AppleBuilder extends SampleHashBuilder {

  AppleBuilder() {
    defaults = {
      departments {
        management {
          def employeeBuilder = new EmployeeBuilder()
          employee employeeBuilder()
        }
      }
    }
  }


}

class EmployeeBuilder extends SampleHashBuilder {
  EmployeeBuilder() {
    defaults = {
      firstname "Steve"
      lastname "Jobs"
      title "CEO"
    }

    deriveds = {
      fullname "$c.firstname $c.lastname"
    }
  }


}