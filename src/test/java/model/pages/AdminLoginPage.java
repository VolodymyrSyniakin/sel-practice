package model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminLoginPage extends Page {

	private final static String regexURL = ".*controller=AdminLogin.*";

	private String emailIdStr = "email";
	private String passIdStr = "passwd";
	private String subLoginNameStr = "submitLogin";
	private String errorIdStr = "error";

	public AdminLoginPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
		wait.until(ExpectedConditions.urlMatches(regexURL));
	}
	
	public AdminDashboardPage submitLogin(String email, String password) {
		writeText(By.id(emailIdStr), email);
		writeText(By.id(passIdStr), password);
		click(By.name(subLoginNameStr));
		return new AdminDashboardPage(this.driver, this.wait);
	}

	public static String getRegexurl() {
		return regexURL;
	}
	
	public String getTextError () {
		return find(By.id(errorIdStr)).getText();
	}
}
