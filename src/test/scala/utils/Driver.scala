package utils

import java.util.Properties

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService}

object Driver {

  val systemProperties: Properties = System.getProperties

  lazy val ifMac: Boolean = systemProperties.getProperty("os.name").startsWith("Mac")

  if (ifMac) {
    systemProperties.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,"./chrome/chromedriverMac")
  } else {
    systemProperties.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,"./chrome/chromedriver")
  }

  val instance: WebDriver = newWebDriver()

  def newWebDriver(): WebDriver = {
    val browserProperty = systemProperties.getProperty("browser", "chrome")
    val driver = createDriver(browserProperty)
    driver
  }

  private def createDriver(browserProperty: String): WebDriver = {
    browserProperty match {
      case "chrome" => createChromeBrowser()
      case _ => throw new IllegalArgumentException(s"browser type $browserProperty not recognised ")
    }
  }

  private def createChromeBrowser(): WebDriver = {
    val driver = new ChromeDriver()
    driver
  }

  sys addShutdownHook instance.quit()

}
