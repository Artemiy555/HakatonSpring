Feature: Check registration form @priority1

  Background:
    Given I open registration page

  @R001 @all @smoke @positive
  Scenario: POS_Check registration page elements
  Then I see username, e-mail, pass, confirm pass fields signUp and signUp buttons



  @R002 @all @smoke @positive
  Scenario: Valid Registration Form Information
    When I enter valid username
    And I enter valid email
    And I enter valid pass
    And I enter valid confirmation pass
    And User clicks sign up btn
    Then System proceeds with registration

  @R005 @all @smoke @negative
  Scenario: NEG_Check registration new user with existing username
    When I enter already registred username
    And I enter valid email
    And I enter valid pass
    And I enter valid confirmation pass
    And User clicks sign up btn
    Then System shows error message

  @R008 @all @smoke @positive
  Scenario: POS_Check registration new user with uppercase username
    When I enter already registred username in uppercase
    And I enter valid email
    And I enter valid pass
    And I enter valid confirmation pass
    And User clicks sign up btn
    Then System proceeds with registration

  @R014 @all @smoke @negative
  Scenario: NEG_Check registration new user with uppercase email
    When I enter valid username
    And I enter already registred email in uppercase
    And I enter valid pass
    And I enter valid confirmation pass
    And User clicks sign up btn
    Then System shows error message

  @R023 @all @smoke @negative
  Scenario: NEG_Check registration new user with lowrecase pass and uppercase confirmation pass
    When I enter valid username
    And I enter valid email
    And I enter valid pass in lowercase
    And User submits pass in uppercase in confirmation pass
    Then System shows error message

  @R010 @all @negative
  Scenario: NEG_Check registration with empty email
    When I enter valid username
    And I enter empty email
    And I enter valid pass
    And I enter valid confirmation pass
    Then System shows error message

  @R010 @all @negative
  Scenario: NEG_Check registration with email without symbols before @
    When I enter valid username
    And User submits email without symbols before @
    And I enter valid pass
    And I enter valid confirmation pass
    Then System shows error message

  @R010 @all @negative
  Scenario: NEG_Check registration with email with spaces
    When I enter valid username
    And User submits email with spaces
    And I enter valid pass
    And I enter valid confirmation pass
    Then System shows error message

  @R017 @all  @negative
  Scenario: NEG_Check registration with empty password
    When I enter valid username
    And I enter valid email
    And I entern empty pass
    And I enter valid confirmation pass
    Then System shows error message

  @R017 @all @negative
  Scenario: NEG_Check registration with short password
    When I enter valid username
    And I enter valid email
    And User submits short pass
    And User submits short pass confirmation pass
    Then System shows error message

  @R017 @all @negative
  Scenario: NEG_Check registration with digits password
    When I enter valid username
    And I enter valid email
    And User submits digits pass
    And User submits digits pass confirmation
    Then System shows error message

  @R017 @all @negative
  Scenario: NEG_Check registration with lowercase password
    When I enter valid username
    And I enter valid email
    And User submits lowercase pass
    And User submits lowercase confirmation
    Then System shows error message

  @R022 @all
  Scenario: NEG_Check registration with non_valid confirm password
    When I enter valid username
    And I enter valid email
    And I enter valid pass
    And User submits differnet pass in confirmation field
    Then System shows error message







