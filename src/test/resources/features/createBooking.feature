@wip

Feature: Create Booking
  As a User
  I want to create a booking using the hotel booking application
  so that I can see my booking confirmed

  Scenario: Create booking
    Given I am on the booking app home page
    When I create the booking with the following details
      | First name | Surname | Price | Deposit | Check in   | Check out  |
      | Test1      | Sur1    | 125   | true    | 2018-06-17 | 2018-06-18 |
      | Test2      | Sur2    | 65    | false   | 2018-06-25 | 2018-06-26 |
    Then I should see the bookings created
      | Surname |
      | Sur1    |
      | Sur2    |

