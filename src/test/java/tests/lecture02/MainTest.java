package tests.lecture02;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import model.pages.AdminDashboardPage;
import model.pages.AdminLoginPage;
import tests.BaseTest;
import utils.Properties;

public class MainTest extends BaseTest {

	private AdminLoginPage loginPage;
	private AdminDashboardPage dashboard;

	@Test(priority = 1)
	@Parameters({ "login", "password" })
	public void signInAndOut(String login, String password) {
		driver.get(Properties.getBaseAdminUrl());
		loginPage = new AdminLoginPage(driver, wait);
		dashboard = loginPage.submitLogin(login, password);
		dashboard.getOut();
	}

	@Test(priority = 2)
	@Parameters({ "login", "password" })
	public void clickAllIntoMenuDashboard(String login, String password) {
		if (!driver.getCurrentUrl().matches(AdminDashboardPage.getRegexurl())) {
			if (!driver.getCurrentUrl().matches(AdminLoginPage.getRegexurl())) {
				driver.get(Properties.getBaseAdminUrl());
			}
			dashboard = new AdminLoginPage(driver, wait).submitLogin(login, password);
		}

		List<String> idLinks = dashboard.getIdMainLi();
		for (String id : idLinks) {
			driver.findElement(By.cssSelector("#" + id + " > a")).click();

			System.out.println(driver.getTitle());
			String url = driver.getCurrentUrl();
			driver.navigate().refresh();

			assertEquals(driver.getCurrentUrl(), url, "refresh page - fail, by url: " + driver.getCurrentUrl());
		}
	}
}
