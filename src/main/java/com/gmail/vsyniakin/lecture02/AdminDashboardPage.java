package com.gmail.vsyniakin.lecture02;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminDashboardPage {
	private final WebDriver driver;
	
	private final String regexURL = ".*AdminDashboard.*";
	
	private static By getOutLoc = By.id("header_logout");
	
	private static By emplInfLoc = By.id("employee_infos");
	
	private static By dashboardLinkLoc = By.linkText("Dashboard");
	
	private static By mainTabLoc = By.className("maintab");
	
	private static By titleLinkLoc = By.className("title");

	public AdminDashboardPage(WebDriver driver) {
		super();
		
		WebElement dashboardLinkEl = driver.findElement(dashboardLinkLoc);
		if (dashboardLinkEl!=null) {
			if (!driver.getCurrentUrl().matches(regexURL)) {
				throw new IllegalStateException("This is not AdminDashboardPage!");
			}
		}
		this.driver = driver;
	}
	
	public AdminLoginPage getOut () {
		WebElement emplInfEl = driver.findElement(emplInfLoc);
		emplInfEl.click();
		
		WebElement getOutEl = driver.findElement(getOutLoc);
		getOutEl.click();
		
		return new AdminLoginPage(driver);
	}
	
	public List<String> getIdMainLi () {
		List<WebElement> mainTab = driver.findElements(mainTabLoc);
		List<String> idLi = new ArrayList<String>();
		for (WebElement element : mainTab) {
			try {
				idLi.add(element.getAttribute("id"));
			} catch (NoSuchElementException e) {}
		}
		return idLi;
	}
	
	public static By getGetOutLoc() {
		return getOutLoc;
	}

	public static void setGetOutLoc(By getOutLoc) {
		AdminDashboardPage.getOutLoc = getOutLoc;
	}

	public static By getEmplInfLoc() {
		return emplInfLoc;
	}

	public static void setEmplInfLoc(By emplInfLoc) {
		AdminDashboardPage.emplInfLoc = emplInfLoc;
	}

	public static By getDashboardLinkLoc() {
		return dashboardLinkLoc;
	}

	public static void setDashboardLinkLoc(By dashboardLinkLoc) {
		AdminDashboardPage.dashboardLinkLoc = dashboardLinkLoc;
	}

	public static By getMainTabLoc() {
		return mainTabLoc;
	}

	public static void setMainTabLoc(By mainTabLoc) {
		AdminDashboardPage.mainTabLoc = mainTabLoc;
	}

	public static By getTitleLinkLoc() {
		return titleLinkLoc;
	}

	public static void setTitleLinkLoc(By titleLinkLoc) {
		AdminDashboardPage.titleLinkLoc = titleLinkLoc;
	}


	
	
}
