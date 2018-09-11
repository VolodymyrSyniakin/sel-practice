package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
	/**
	 *
	 * @param browser Driver type to use in tests.
	 * @return New instance of {@link WebDriver} object.
	 */
	public static WebDriver initDriver(String browser) {
		switch (browser) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver",
					new File(DriverFactory.class.getResource("/geckodriver.exe").getFile()).getPath());
			return new FirefoxDriver();
		case "ie":
		case "internet explorer":
			System.setProperty("webdriver.ie.driver",
					new File(DriverFactory.class.getResource("/IEDriverServer.exe").getFile()).getPath());
			InternetExplorerOptions ieOptions = new InternetExplorerOptions().destructivelyEnsureCleanSession();
			ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			return new InternetExplorerDriver(ieOptions);
		case "android":
			System.setProperty("webdriver.chrome.driver",
					new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
			Map<String, String> androidEmulation = new HashMap<>();
			androidEmulation.put("deviceName", "Nexus 5");
			ChromeOptions chrOptions = new ChromeOptions();
			chrOptions.setExperimentalOption("mobileEmulation", androidEmulation);
			return new ChromeDriver(chrOptions);
		case "chrome":
		default:
			System.setProperty("webdriver.chrome.driver",
					new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
			return new ChromeDriver();
		}
	}

	/**
	 *
	 * @param browser Remote driver type to use in tests.
	 * @param gridUrl URL to Grid.
	 * @return New instance of {@link RemoteWebDriver} object.
	 */
	public static WebDriver initDriver(String browser, String gridUrl) {
		switch (browser) {
		case "android":
			Map<String, String> andrRemoteEmulation = new HashMap<>();
			andrRemoteEmulation.put("deviceName", "Nexus 5");
			ChromeOptions andrRemoteOptions = new ChromeOptions();
			andrRemoteOptions.setExperimentalOption("mobileEmulation", andrRemoteEmulation);
			try {
				return new RemoteWebDriver(new URL(gridUrl), andrRemoteOptions);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case "firefox":
			FirefoxOptions fireRemoteOptions = new FirefoxOptions();
			try {
				return new RemoteWebDriver(new URL(gridUrl), fireRemoteOptions);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case "ie":
			InternetExplorerOptions ieRemoteOptions = new InternetExplorerOptions().destructivelyEnsureCleanSession();
			ieRemoteOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			try {
				return new RemoteWebDriver(new URL(gridUrl), ieRemoteOptions);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case "chrome":
		default:
			ChromeOptions chrRemoteOptions = new ChromeOptions();
			try {
				return new RemoteWebDriver(new URL(gridUrl), chrRemoteOptions);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
