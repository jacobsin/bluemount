Feature: File Upload
  In order to
  As a user
  I want to be able to upload file to the server

  Scenario: upload single file
    When I upload test.txt
    Then it should give these:
      | name     | contentType | size             | fieldName |
      | test.txt | text/plain  | size of test.txt | file1     |

  Scenario: upload multiple files
    When I upload:
      | test.txt |
      | test.pdf |
    Then it should give these:
      | name     | contentType     | size             | fieldName |
      | test.txt | text/plain      | size of test.txt | file1     |
      | test.pdf | application/pdf | size of test.pdf | file2     |