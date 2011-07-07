Feature: Projects
  In order to efficiently manage projects
  As a user
  I want to be able to bulk operate on projects

  Scenario: list projects
    Given project a exists
    And project b exists
    When I list projects
    Then it should give me:
    | title | owner      |
    | a     | Mr Default |
    | b     | Mr Default |

  Scenario: list projects using spring api
    Given spring api is used
    And project a exists
    And project b exists
    When I list projects
    Then it should give me:
    | title | owner      |
    | a     | Mr Spring  |
    | b     | Mr Spring  |
