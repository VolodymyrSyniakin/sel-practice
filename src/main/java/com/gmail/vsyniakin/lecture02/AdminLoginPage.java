package com.gmail.vsyniakin.lecture02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminLoginPage {
	private final WebDriver driver;
	
	private final String regexURL = ".*controller=AdminLogin.*";
	private By emailLoc = By.id("email");
	private By passLoc = By.id("passwd");
	private By subLoginLoc = By.name("submitLogin");
	private By errorLoc = By.id("error");

	public AdminLoginPage(WebDriver driver) {
		super();
		if (!driver.getCurrentUrl().matches(regexURL)) {
			throw new IllegalStateException("This is not the AdminLoginPage!");
		}
		this.driver = driver;
	}
	
	public AdminDashboardPage submitLogin (String email, String password) {
		WebElement emailEl = driver.findElement(emailLoc);
		WebElement passEl = driver.findElement(passLoc);
		WebElement subLoginEl = driver.findElement(subLoginLoc);
		
		emailEl.sendKeys(email);
		passEl.sendKeys(password);
		subLoginEl.submit();
		
		return new AdminDashboardPage(this.driver);
	}

	public By getEmailLoc() {
		return emailLoc;
	}

	public void setEmailLoc(By emailLoc) {
		this.emailLoc = emailLoc;
	}

	public By getPassLoc() {
		return passLoc;
	}

	public void setPassLoc(By passLoc) {
		this.passLoc = passLoc;
	}

	public By getSubLoginLoc() {
		return subLoginLoc;
	}

	public void setSubLoginLoc(By subLoginLoc) {
		this.subLoginLoc = subLoginLoc;
	}

	public By getErrorLoc() {
		return errorLoc;
	}

	public void setErrorLoc(By errorLoc) {
		this.errorLoc = errorLoc;
	}
}
