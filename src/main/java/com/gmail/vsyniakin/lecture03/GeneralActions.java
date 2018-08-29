package com.gmail.vsyniakin.lecture03;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
	private final Logger log;
	private WebDriver driver;
	private WebDriverWait wait;

	public GeneralActions(WebDriver driver, Logger log) {
		super();
		this.driver = driver;
		this.log = log;
		this.wait = new WebDriverWait(driver, 10);
	}

	/**
	 * Logs in to Admin Panel.
	 * 
	 * @param login
	 * @param password
	 */
	public void login(String login, String password) {
		WebElement emailEl = driver.findElement(By.id("email"));
		WebElement passEl = driver.findElement(By.id("passwd"));
		WebElement subLoginEl = driver.findElement(By.name("submitLogin"));

		emailEl.sendKeys(login);
		passEl.sendKeys(password);
		subLoginEl.click();
	}

	/**
	 * Adds new category in Admin Panel.
	 * 
	 * @param categoryName
	 */
	public void createCategory(String categoryName) {
		try {
			if (!driver.getCurrentUrl().matches(".*controller=AdminCategories.*")) {
				goToCategoryPage();
			}

			By newCategoryLinkLoc = By.cssSelector("#page-header-desc-category-new_category");
			WebElement newCategoryLinkEl = getElementIfLocated(newCategoryLinkLoc);
			newCategoryLinkEl.click();

			By nameCategoryInputLoc = By.id("name_1");
			WebElement nameCatEl = getElementIfLocated(nameCategoryInputLoc);
			nameCatEl.sendKeys(categoryName);

			By submitCatLoc = By.cssSelector("#category_form_submit_btn");
			WebElement submitCatEl = driver.findElement(submitCatLoc);
			submitCatEl.click();

			By divSuccessLoc = By.cssSelector(".alert-success");
			try {
				wait.until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(divSuccessLoc)));
			} catch (ElementNotVisibleException e) {
				log.severe("Element by locator -> " + divSuccessLoc + " not visible on page!");
			}
		} catch (NoSuchElementException | TimeoutException e) {
			log.severe(e.toString());
			return;
		}
	}

	public void goToCategoryPage() {
		By catalogLoc = By.cssSelector("#subtab-AdminCatalog");
		WebElement catalogEl = getElementIfLocated(catalogLoc);
		new Actions(driver).moveToElement(catalogEl).perform();

		By catalogsLinkLoc = By.cssSelector("#subtab-AdminCategories > a");
		try {
			wait.until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(catalogsLinkLoc)));
			WebElement catalogsLinkEl = getElementIfLocated(catalogsLinkLoc);
			catalogsLinkEl.click();
		} catch (ElementNotVisibleException e) {
			log.severe(e.toString());
		}
	}

	public void checkCategoryInCategoriesTable(String categoryName) {
		try {
			if (!driver.getCurrentUrl().matches(".*controller=AdminCategories.*")) {
				goToCategoryPage();
			}
			By nameFilterLoc = By.cssSelector("[name=categoryFilter_name]");
			WebElement nameFilterEl = driver.findElement(nameFilterLoc);
			nameFilterEl.sendKeys(categoryName);

			By submitFilterLoc = By.cssSelector("#submitFilterButtoncategory");
			WebElement submitFilterEl = driver.findElement(submitFilterLoc);
			submitFilterEl.click();

			By nameCatLoc = By.xpath("//table/tbody/tr/td[3]");
			List<WebElement> names = getElementsIfLocated(nameCatLoc);

			boolean equalsName = false;
			if (names.size() == 0) {
				log.severe("Category --> " + categoryName + " - not found, on page: " + driver.getCurrentUrl());
			} else {
				for (WebElement webElement : names) {
					if (webElement.getText().equals(categoryName)) {
						if (!equalsName) {
							equalsName = true;
						} else {
							log.warning("More than one category by name --> " + categoryName);
							break;
						}
					}
				}
				if (!equalsName) {
					log.severe("Category --> " + categoryName + " - not found, on page: " + driver.getCurrentUrl());
				}
			}
		} catch (NoSuchElementException | TimeoutException e) {
			log.severe(e.toString());
			return;
		}
	}

	/**
	 * Waits until page load element by locator
	 */
	public WebElement getElementIfLocated(By locator) throws TimeoutException {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public List<WebElement> getElementsIfLocated(By locator) throws TimeoutException {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

}
