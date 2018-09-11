package tests.lecture04;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.gmail.vsyniakin.lecture03.utils.Properties;

import model.ProductData;
import model.pages.AdminCreateProductPage;
import model.pages.AdminDashboardPage;
import model.pages.AdminLoginPage;
import model.pages.AdminProductPage;
import tests.BaseTest;
import utils.logging.CustomReporter;

public class CreateProductTest extends BaseTest {

	private static Object[][] products;
	private static String dashboardURL;

	@DataProvider(name = "ProductData")
	public static Object[][] productData() {
		if (products == null) {
			products = new Object[][] { { ProductData.generate() } };
		}
		return products;

	}

	@BeforeClass
	@Parameters({ "login", "password" })
	public void login(String login, String password) {
		driver.get(Properties.getBaseAdminUrl());
		AdminLoginPage loginPage = new AdminLoginPage(driver, wait);
		loginPage.submitLogin(login, password);
	}

	@Test(enabled = true, dataProvider = "ProductData")
	@Parameters({ "product" })
	public void createNewProduct(ProductData product) {
		CustomReporter.logAction("Create product");
		if (dashboardURL == null) {
			dashboardURL = driver.getCurrentUrl();
		} else {
			driver.get(dashboardURL);
		}
		AdminDashboardPage dashboard = new AdminDashboardPage(driver, wait);
		AdminProductPage productPage = dashboard.goToProducts();
		AdminCreateProductPage createProduct = productPage.goToCreateProduct();
		createProduct.create(product);
	}

	@Test(enabled = true, dataProvider = "ProductData")
	@Parameters({ "product" })
	public void visibleProductOnWebSite(ProductData product) {
		CustomReporter.logAction("Visible Product");
		driver.get(Properties.getBaseUrl());
		driver.findElement(By.className("all-product-link")).click();

		WebElement linkProduct = searchProduct(product);
		linkProduct.click();

		String resQty = driver.findElement(By.cssSelector(".product-quantities > span")).getText();
		assertTrue(resQty.contains(product.getQty().toString()),
				"incorrect quantity: expected -> " + product.getQty() + ", display -> " + resQty);

		String resPrice = driver.findElement(By.cssSelector(".current-price > span")).getText();
		assertTrue(resPrice.contains(product.getPriceStr()),
				"incorrect price: expected -> " + product.getPriceStr() + ", display -> " + resPrice);

		String resName = driver.findElement(By.tagName("h1")).getText();
		assertEquals(resName, product.getName().toUpperCase(), "incorrect name:");
	}

	public WebElement searchProduct(ProductData product) {
		boolean productFound = false;
		WebElement linkProduct = null;
		try {
			linkProduct = driver.findElement(By.linkText(product.getName()));
			productFound = true;
		} catch (NoSuchElementException e) {
			WebElement next = driver.findElement(By.className("next"));
			if (!next.getAttribute("class").contains("disabled")) {
				driver.findElement(By.className("next")).click();
				return searchProduct(product);
			} else {
				assertEquals(productFound, true, product.getName() + " not found on products page");
			}
		}
		return linkProduct;
	}

	@AfterTest
	public void clearData() {
		dashboardURL = null;
		products = null;
	}

}
