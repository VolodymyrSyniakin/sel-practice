package model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminProductPage extends BaseAdminPageElements {
	private final static String regexURL = ".*product/catalog.*";
	
	private String createProdLinkIdStr = "page-header-desc-configuration-add";
	
	public AdminProductPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
		wait.until(ExpectedConditions.urlMatches(regexURL));
	}

	public static String getRegexURL() {
		return regexURL;
	}
	
	public AdminCreateProductPage goToCreateProduct () {
		find(By.id(createProdLinkIdStr)).click();
		//wait.until(ExpectedConditions.elementToBeClickable()).click();
		return new AdminCreateProductPage(driver, wait);
	}
	
	
	
}
