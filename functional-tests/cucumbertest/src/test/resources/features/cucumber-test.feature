Feature:
  As a cucumber user I want to test scenario outline

  Scenario Outline:
    When I add correlation id "<correlation_id>"
    Then I query for the correlation id "<correlation_id>" I should see it

    Examples:
      | correlation_id |
      | 123 |
      | 456 |
      | 789 |