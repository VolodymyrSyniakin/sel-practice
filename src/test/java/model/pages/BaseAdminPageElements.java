package model.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseAdminPageElements extends Page {

	private String getOutIdStr = "header_logout";

	private String emplInfIdStr = "employee_infos";

	private String mainTabClassStr = "maintab";

	private String catIdStr = "subtab-AdminCatalog";

	protected BaseAdminPageElements(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}

	public AdminLoginPage getOut() {
		driver.findElement(By.id(emplInfIdStr)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id(getOutIdStr))).click();
		return new AdminLoginPage(driver, wait);
	}

	public AdminProductPage goToProducts() {
		click(By.id(catIdStr));
		return new AdminProductPage(driver, wait);
	}

	public List<String> getIdMainLi() {
		List<WebElement> mainTab = driver.findElements(By.className(mainTabClassStr));
		List<String> idLiStr = new ArrayList<String>();
		for (WebElement element : mainTab) {
			try {
				idLiStr.add(element.getAttribute("id"));
			} catch (NoSuchElementException e) {
			}
		}
		return idLiStr;
	}
	
}
