Feature: Contacts List

  Scenario: a request is made for a registered user and domain
    When a user's contacts list is requested
    Then display contacts list

  Scenario: get contacts for a user that has no contacts
    When a user's contacts list is requested and they have no contacts
    Then no contacts are shown

  Scenario: access a domain that does not exist
    When get contacts for user on nonexistent domain
    Then display an error

  Scenario: try to update an existing user's contacts list
    When an update request is made
    Then update all phone #s and email addresses
    And display contacts list

  Scenario: try to update a nonexistent user's contacts list
    When an update request is made for non-existing contacts
    Then display an error