package tests.lecture05;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import model.Customer;
import model.ProductData;
import tests.BaseTest;
import utils.DataConverter;
import utils.Properties;

public class PlaceOrderTest extends BaseTest {
	
	private ProductData product;
	private int qtyOrder;
	
	@Test
	public void checkSiteVersion() {
		driver.get(Properties.getBaseUrl());
		WebElement element = driver.findElement(By.className("mobile"));
		if (isMobileTesting) {
			assertTrue(!element.getCssValue("display").equals("none"),
					"Invalid site version: need mobile, load desktop.");
		} else {
			assertTrue(element.getCssValue("display").equals("none"),
					"Invalid site version: need desktop, load mobile.");
		}
	}


	@Test (dependsOnMethods = {"checkSiteVersion"})
	public void createNewOrder() {
		goToProduct();
		
		product = getProductData();
		qtyOrder = 1;

		addToCart(qtyOrder);
		checkCart(product, qtyOrder);

		driver.findElement(By.cssSelector(".cart-detailed-actions > * > a")).click();

		writeOrderData();

		driver.findElement(By.cssSelector("#payment-confirmation button")).click();
	}
	
	
	private void goToProduct() {
		driver.findElement(By.className("all-product-link")).click();

		List<WebElement> products = driver.findElements(By.className("product-miniature"));
		products.get(randomProduct(products.size())).findElement(By.tagName("a")).click();
	}
	private int randomProduct(int length) {
		return (int) (Math.random() * length);
	}
	
	private ProductData getProductData() {

		WebElement productDetails = driver.findElement(By.id("product-details"));
		String details = productDetails.getAttribute("data-product");

		String name = parse(details, "\"name\"").replaceAll("\"", "");
		String price = parse(details, "\"price\"");
		String qty = parse(details, "\"quantity\"");

		return new ProductData(name, Integer.parseInt(qty), Float.parseFloat(price));
	}
	private String parse(String data, String key) {
		int start = data.indexOf(key);
		return data.substring(data.indexOf(":", start) + 1, data.indexOf(",", start));
	}
	
	private void addToCart(int qtyOrder) {
		WebElement qtyEl = driver.findElement(By.id("quantity_wanted"));
		qtyEl.sendKeys(Keys.BACK_SPACE);
		qtyEl.sendKeys(Integer.toString(qtyOrder));

		driver.findElement(By.className("add-to-cart")).click();

		WebElement element = driver.findElement(By.cssSelector(".cart-content > a"));
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}
	
	private void checkCart(ProductData product, int inOrder) {
		String name = driver.findElement(By.cssSelector(".product-line-info > a")).getText();
		assertEquals(name, product.getName(), "Incorrect name: ");

		String priceField = driver.findElement(By.cssSelector(".product-line-info > span")).getText();
		String price = priceField.split(" ")[0];
		assertEquals(price, product.getPriceStr(), "Incorrect price: ");

		int order = Integer.parseInt(driver.findElement(By.name("product-quantity-spin")).getAttribute("value"));
		assertEquals(order, inOrder, "Incorrect quantity in cart: ");
	}
	
	private void writeOrderData() {
		Customer customer = Customer.generate();
		WebElement customerForm = driver.findElement(By.id("customer-form"));
		customerForm.findElement(By.cssSelector("input[name=firstname]")).sendKeys(customer.getFirstName());
		customerForm.findElement(By.cssSelector("input[name=lastname]")).sendKeys(customer.getSecondName());
		customerForm.findElement(By.cssSelector("input[name=email]")).sendKeys(customer.getEmail());
		customerForm.findElement(By.name("continue")).click();

		WebElement deliveryAddress = driver.findElement(By.id("delivery-address"));
		deliveryAddress.findElement(By.cssSelector("input[name=address1]")).sendKeys(customer.getAddress());
		deliveryAddress.findElement(By.cssSelector("input[name=postcode]"))
				.sendKeys(String.valueOf(customer.getPostcode()));
		deliveryAddress.findElement(By.cssSelector("input[name=city]")).sendKeys(customer.getCity());
		deliveryAddress.findElement(By.name("confirm-addresses")).click();

		driver.findElement(By.name("confirmDeliveryOption")).click();

		List<WebElement> payments = driver.findElements(By.cssSelector("input[name=payment-option]"));
		payments.get((int) (Math.random() * payments.size())).click();
		driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();
	}
	

	@Test (dependsOnMethods = {"createNewOrder"})
	public void confirmOrderTest() {
		assertTrue(isElementPresent(By.className("done")), "Confirm massage not visible");
	
		List<WebElement> lines = driver.findElements(By.className("order-line"));
		assertEquals(lines.size(), 1, "Incorrect count of products");

		String details = driver.findElement(By.cssSelector(".details span")).getText().toLowerCase();
		assertTrue(details.contains(product.getName().toLowerCase()), "Incorrect name, on details line: " + details);

		List<WebElement> elements = driver.findElements(By.cssSelector(".qty div div"));

		float price = DataConverter.parsePriceValue(elements.get(0).getText());
		assertEquals(price, product.getPrice(), "Incorrect price");

		int qty = Integer.parseInt(elements.get(1).getText());
		assertEquals(qty, qtyOrder, "Incorrect quantity");
	}
	
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	@Test (dependsOnMethods = {"confirmOrderTest"})
	public void checkStockQty() {
		driver.findElement(By.className("ui-autocomplete-input")).sendKeys(product.getName());
		driver.findElement(By.cssSelector("#search_widget button")).click();
		driver.findElement(By.linkText(product.getName())).click();
		driver.findElement(By.cssSelector("a[href='#product-details']")).click();
		String stockQtyStr = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-quantities span")))
				.getText();

		int stockQty = DataConverter.parseStockValue(stockQtyStr);
		assertEquals(stockQty, ((int) product.getQty() - 1), "Incorrect stock quantity");
	}
}
