Feature: Projects
  In order to efficiently manage projects
  As a user
  I want to be able to bulk operate on projects

  Scenario: create open source project
    When I create OpenSource project:
    | title   | a        |
    | owner   | Mr Owner |
    | license | LGPL     |
    Then it should give:
    | title   | a        |
    | owner   | Mr Owner |
    | license | LGPL     |

  Scenario: create close source project
    When I create CloseSource project:
    | title     | a        |
    | owner     | Mr Owner |
    | copyright | Company  |
    Then it should give:
    | title     | a        |
    | owner     | Mr Owner |
    | copyright | Company  |

  Scenario: list projects
    Given OpenSource project a exists
    And CloseSource project b exists
    When I list projects
    Then it should give at least these:
    | title | owner      |
    | a     | Mr Default |
    | b     | Mr Default |

  Scenario: list projects using spring api
    Given spring api is used
    And OpenSource project a exists
    And CloseSource project b exists
    When I list projects
    Then it should give at least these:
    | title | owner     |
    | a     | Mr Spring |
    | b     | Mr Spring |
