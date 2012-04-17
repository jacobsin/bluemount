package bluemount.core.model

import org.junit.Ignore
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

  class BuilderWithDefaults extends HashBuilder {
    def defaults = {
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

    BuilderWithDefaults() {
      def defaultsBuilder = new HashBuilder()
      this.content = defaultsBuilder(defaults)
    }
  }

  @Test
  def void buildWithDefaults() {
    def apple = new BuilderWithDefaults()
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

  @Test @Ignore('wip')
  def void buildWithOverridenDefaults() {
    def apple = new BuilderWithDefaults()
    def actual = apple {
      departments {
        management {
          employee {
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
