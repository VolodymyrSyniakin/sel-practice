package com.gmail.vsyniakin.lecture02;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class PracticeOne {
	
	public static void main(String[] args) {
		WebDriver driver = initFirefoxDriver("src/main/resources/geckodriver.exe");
		driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
		setTimeOuts(driver, 5);
		
// task 1:		
		signInAndOut(driver);
		
// task 2:
		clickAllIntoMenuDashboard(driver);
		
		driver.close();
	}
	
/**Логин в Админ панель:
* 	1. Открыть страницу Админ панели
*	2. Ввести логин, пароль и нажать кнопку Логин.
*	3. После входа в систему нажать на пиктограмме пользователя в верхнем правом углу и выбрать опцию «Выход.»
* */
	public static void signInAndOut(WebDriver driver) {
		AdminLoginPage loginPage = new AdminLoginPage(driver);
		AdminDashboardPage adminDashPage = loginPage.submitLogin("webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw");
		loginPage = adminDashPage.getOut();
	}
/**Проверка работоспособности главного меню Админ панели: 
*   1. Войти в Админ панель (по аналогии с предыдущим скриптом)
*	2. Кликнуть на каждом видимом пункте главного меню (Dashboard, Заказы, Каталог, Клиенты…) для открытия соответствующего раздела и выполнить следующее:
*		a. Вывести в консоль заголовок открытого раздела.
*		b. Выполнить обновление (рефреш) страницы и проверить, что пользователь остается в том же разделе после перезагрузки страницы.
* */	
	public static void clickAllIntoMenuDashboard (WebDriver driver) {
		AdminLoginPage loginPage = new AdminLoginPage(driver);
		AdminDashboardPage adminDashPage = loginPage.submitLogin("webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw");
		List<String> idLinks = adminDashPage.getIdMainLi();
		for (String id : idLinks) {
			driver = clickLinkMainMenu(id, driver);
			
			String title = driver.getTitle();
			System.out.println(title);
			
			driver.navigate().refresh();

			if (!title.equals(driver.getTitle())) {
				System.err.println("Test for refresh page - fail, by url: " + driver.getCurrentUrl());
			}
		}
	}
	
	public static WebDriver clickLinkMainMenu(String id, WebDriver driver) {
		try {
			driver.findElement(By.id(id)).findElement(AdminDashboardPage.getTitleLinkLoc()).click();
		} catch (NoSuchElementException e) {
			System.err.println("Next element by id=" + id + "; " + "not found! " + "on url: " + driver.getCurrentUrl());
			driver.navigate().back();
			return clickLinkMainMenu(id, driver);
		}
		return driver;
	}
	
	public static WebDriver initFirefoxDriver (String path) {
		System.setProperty("webdriver.gecko.driver", path);
		return new FirefoxDriver();
	}
	
	public static WebDriver setTimeOuts (WebDriver driver, long sec) {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(sec, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(sec, TimeUnit.SECONDS);
		return driver;
	}
}
