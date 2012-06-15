package bluemount.core.builder

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
}