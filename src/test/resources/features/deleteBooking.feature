@wip
Feature: Delete Booking
  As a User
  I want to delete a booking from the application
  so that I can confirm my booking deleted

  Scenario: Delete booking
    Given I am on the booking app home page
    When I delete the following bookings
      | First name |
      | Test1      |
      | Test2      |
    Then I should not see the deleted bookings