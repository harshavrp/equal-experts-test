package stepdefs

import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import pages.WebPage
import utils.StartUpTearDown

class BookingSteps extends Matchers with EN with ScalaDsl with WebPage with StartUpTearDown{

  Given("""^I am on the booking app home page$""") { () =>
    launchHotelAppPage
  }

  When("""^I create the booking with the following details$""") { table: DataTable =>
    createBooking(table)
  }

  Then("""^I should see the bookings created$""") { table: DataTable =>
    checkBookings(table)
  }

  When("""^I delete the following bookings$"""){ table:DataTable =>
    deleteBooking(table)
  }

  Then("""^I should not see the deleted bookings$"""){ () =>
    verifyDeletedBookings
  }

}
