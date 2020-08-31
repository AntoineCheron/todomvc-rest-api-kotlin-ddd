Feature: Todo

  Scenario: Updating a todo
    Given I have to 'go to the grocery store'
    When I complete it
    Then My todo should be completed
