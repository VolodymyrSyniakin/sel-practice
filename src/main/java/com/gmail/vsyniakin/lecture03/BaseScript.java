package com.gmail.vsyniakin.lecture03;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.gmail.vsyniakin.lecture03.utils.Properties;

import java.io.File;
import java.util.logging.Logger;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public abstract class BaseScript {

	/**
	 *
	 * @return New instance of {@link WebDriver} object. Driver type is based on
	 *         passed parameters to the automation project, returns
	 *         {@link ChromeDriver} instance by default.
	 */
	public static WebDriver getDriver() {
		String browser = Properties.getBrowser();

		switch (browser) {
		case BrowserType.FIREFOX:
			System.setProperty("webdriver.gecko.driver",
					new File(BaseScript.class.getResource("/geckodriver.exe").getFile()).getPath());
			return new FirefoxDriver();
		case BrowserType.IEXPLORE:
			System.setProperty("webdriver.ie.driver",
					new File(BaseScript.class.getResource("/IEDriverServer.exe").getFile()).getPath());
			return new InternetExplorerDriver();
		default:
			System.setProperty("webdriver.chrome.driver",
					new File(BaseScript.class.getResource("/chromedriver.exe").getFile()).getPath());
			return new ChromeDriver();
		}
	}

	/**
	 * Creates {@link WebDriver} instance with timeout and browser window
	 * configurations.
	 *
	 * @return New instance of {@link EventFiringWebDriver} object. Driver type is
	 *         based on passed parameters to the automation project, returns
	 *         {@link ChromeDriver} instance by default.
	 */
	public static EventFiringWebDriver getConfiguredDriver(Logger log) {
		WebDriver driver = getDriver();
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		eventDriver.register(new EventListener(log));
		eventDriver.get(Properties.getBaseAdminUrl());
		return eventDriver;
	}
}
