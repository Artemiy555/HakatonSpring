#Feature: Check authorization form @priority1
#
#  Background:
#    Given I open authorization page
#
#   @A001 @all @smoke @positive
#  Scenario: POS_Check authorization page elements
#    Then I see username and pass fields
#    And I see signIn and signIn with Google buttons
#    And I see sign up link
#
#  @A002 @all @smoke @positive
#  Scenario: POS_Check authorization of existing user
#    When I enter valid email
#    And I enter valid pass
#    And I click signIn btn
#    Then System redirects me to My Acc page
#
#  @A005 @all @smoke @negative
#  Scenario: NEG_Check authorization with empty email
#    When I enter empty email
#    And I enter valid pass
#    And I click signIn btn
#    Then System shows error message
#
#  @A006 @all @smoke @negative
#  Scenario: NEG_Check authorization with pass with spaces
#    When I enter valid email
#    And I enter valid pass with spaces
#    And I click signIn btn
#    Then System shows error message
#
#  @A006 @all @smoke @negative
#  Scenario: NEG_Check authorization with empty pass
#    When I enter valid email
#    And I enter empty pass
#    And I click signIn btn
#    Then System shows error message
#
#  @A0011 @all @positive
#  Scenario: POS_Check authorization uppercase email
#    When I enter valid email in uppercase
#    And I enter valid  pass
#    And I click signIn btn
#    Then System redirects me to My Acc page
#
