package model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {

	protected WebDriver driver;
	protected WebDriverWait wait;

	protected Page(WebDriver driver, WebDriverWait wait) {
		super();
		this.driver = driver;
		this.wait = wait;
	}

	public void click(By locator) {
		driver.findElement(locator).click();
	}

	public void writeText(By locator, String text) {
		driver.findElement(locator).sendKeys(text);
	}

	public void writeText(WebElement element, String text) {
		element.sendKeys(text);
	}

	public WebElement find(By locator) {
		return driver.findElement(locator);
	}

	public void moveTo(WebElement element) {
		new Actions(driver).moveToElement(element).perform();
	}

}
