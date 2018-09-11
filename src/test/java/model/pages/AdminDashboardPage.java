package model.pages;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminDashboardPage extends BaseAdminPageElements {

	private final static String regexURL = ".*AdminDashboard.*";

	public AdminDashboardPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
		wait.until(ExpectedConditions.urlMatches(regexURL));
	}

	public static String getRegexurl() {
		return regexURL;
	}
}
