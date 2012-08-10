Feature: File Upload
  In order to
  As a user
  I want to be able to upload file to the server

  Scenario: upload file
    When I upload test.txt
    Then it should give these:
      | name     | contentType | size             | fieldName |
      | test.txt | text/plain  | size of test.txt | myfile    |