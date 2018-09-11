package tests;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utils.DriverFactory;
import utils.logging.EventHandler;

import java.util.concurrent.TimeUnit;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public abstract class BaseTest {
	protected EventFiringWebDriver driver;
	protected WebDriverWait wait;
	protected boolean isMobileTesting;

	@BeforeClass
	@Parameters({ "selenium.browser", "selenium.grid" })
	public void setUp(@Optional("chrome") String browser, @Optional("") String gridUrl) {
		if (gridUrl.isEmpty()) {
			driver = new EventFiringWebDriver(DriverFactory.initDriver(browser));
		} else {
			driver = new EventFiringWebDriver(DriverFactory.initDriver(browser, gridUrl));
		}

		driver.register(new EventHandler());

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		isMobileTesting = isMobileTesting(browser);
		if (!isMobileTesting) {
			driver.manage().window().maximize();
		}

		wait = new WebDriverWait(driver, 15);
	}

	/**
	 * Closes driver instance after test class execution.
	 */
	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 *
	 * @return Whether required browser displays content in mobile mode.
	 */
	private boolean isMobileTesting(String browser) {
		switch (browser) {
		case "android":
			return true;
		case "firefox":
		case "ie":
		case "internet explorer":
		case "chrome":
		default:
			return false;
		}
	}
}
