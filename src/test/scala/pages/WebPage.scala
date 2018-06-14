package pages

import java.util
import java.util.concurrent.TimeUnit

import cucumber.api.DataTable
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.{ExpectedConditions, Select, WebDriverWait}
import org.scalatest.Matchers
import utils.StartUpTearDown

import scala.collection.JavaConversions

trait WebPage extends Matchers with StartUpTearDown {

  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
  var ids: List[String] = List[String]()

  def createBooking(table: DataTable): Unit = {
    val data: List[util.Map[String, String]] = JavaConversions.asScalaBuffer(table.asMaps(classOf[String], classOf[String])).toList
    data.foreach {
      i => {
        var firstname = i.get("First name")
        driver.findElement(By.id("firstname")).sendKeys(i.get("First name"))
        driver.findElement(By.id("lastname")).sendKeys(i.get("Surname"))
        driver.findElement(By.id("totalprice")).sendKeys(i.get("Price"))
        val select = new Select(driver.findElement(By.id("depositpaid")))
        select.selectByVisibleText(i.get("Deposit"))
        driver.findElement(By.id("checkin")).sendKeys(i.get("Check in"))
        driver.findElement(By.id("checkout")).sendKeys(i.get("Check out"))
        driver.findElement(By.xpath("//*[@value=' Save ']")).click()
        driver.navigate().refresh()
        var id = driver.findElement(By.xpath(s"//div[@id='bookings']/div/div/p[contains(text(),'$firstname')]/ancestor::div[@class='row']")).getAttribute("id")
        ids = id :: ids
      }
    }
  }

  def checkBookings(table: DataTable): Unit = {
    val data: util.List[util.Map[String, String]] = table.asMaps(classOf[String], classOf[String])
    ids.foreach {
      var index = data.size() - 1
      i => {
        driver.findElement(By.xpath(s"//*[@id=$i]/div[2]/p")).getText should be(data.get(index).get("Surname"))
      }
        index -= 1
    }
  }

  def deleteBooking(table: DataTable): Unit = {
    val data: List[util.Map[String, String]] = JavaConversions.asScalaBuffer(table.asMaps(classOf[String], classOf[String])).toList
    data.foreach {
      i => {
        var fname = i.get("First name")
        var fn = driver.findElement(By.xpath(s"//div[@id='bookings']/div/div/p[contains(text(),'$fname')]/ancestor::div[@class='row']")).getAttribute("id")
        driver.findElement(By.xpath(s"//input[@onclick='deleteBooking($fn)']")).click()
      }
    }
  }

  def launchHotelAppPage: Unit = {
    driver.get("http://hotel-test.equalexperts.io/")
    val actualPageTitle = driver.getTitle
    val expectedPageTitle = "Hotel booking form"
    actualPageTitle shouldBe expectedPageTitle
  }

  def verifyDeletedBookings: Unit = {
    ids.foreach(i => new WebDriverWait(driver, 10).
      until(ExpectedConditions.invisibilityOfElementLocated(
        By.xpath(s"//input[@onclick='deleteBooking($i)']"))))
  }
}
