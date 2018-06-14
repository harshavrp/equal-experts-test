package utils

import org.openqa.selenium.WebDriver

trait StartUpTearDown {
  implicit val driver: WebDriver = Driver.instance
}
