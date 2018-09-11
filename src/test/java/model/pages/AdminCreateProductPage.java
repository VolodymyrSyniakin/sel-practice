package model.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.ProductData;


public class AdminCreateProductPage extends BaseAdminPageElements{
	private final static String regexURL = ".*product/form.*";
	
	private String nameInputIdStr = "form_step1_name_1";
	private String qtyInputIdStr = "form_step1_qty_0_shortcut";
	private String priceInputIdStr = "form_step1_price_ttc_shortcut";
	private String switchProdClassStr = "switch-input";
	private String onlineProdClassStr = "online-title";
	private String growlMassageClassStr = "growl-message";
	private String growlCloseClassStr = "growl-close";
	private String submitProdIdStr = "submit";
	

	public AdminCreateProductPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
		wait.until(ExpectedConditions.urlMatches(regexURL));
	}

	public void create (ProductData product) {
		writeText(By.id(nameInputIdStr), product.getName());
		writeText(By.id(qtyInputIdStr), product.getQty().toString());
		writeText(By.id(priceInputIdStr), product.getPriceStr());
	
		click(By.className(switchProdClassStr));
		List<WebElement> checkedElem = new ArrayList<>();
		checkedElem.add(driver.findElement(By.className(onlineProdClassStr)));
		checkedElem.add(driver.findElement(By.className(growlMassageClassStr)));
		if (wait.until(ExpectedConditions.visibilityOfAllElements(checkedElem)) != null) {
			click(By.className(growlCloseClassStr));
			click(By.id(submitProdIdStr));
		} else {
			throw new IllegalArgumentException ("elements by locator name: " + onlineProdClassStr + ", " + growlMassageClassStr + " -> not visible, by url: " + driver.getCurrentUrl());
		}
		if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(growlMassageClassStr))) != null) {
			click(By.className(growlCloseClassStr));
		} else {
			throw new IllegalArgumentException ("elements by locator name: " + growlMassageClassStr + " -> not visible, by url:" + driver.getCurrentUrl());
		}
	}
	
	public static String getRegexurl() {
		return regexURL;
	}

}
