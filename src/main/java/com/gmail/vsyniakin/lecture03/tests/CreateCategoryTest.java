package com.gmail.vsyniakin.lecture03.tests;

import java.util.logging.Logger;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.gmail.vsyniakin.lecture03.BaseScript;
import com.gmail.vsyniakin.lecture03.GeneralActions;

public class CreateCategoryTest extends BaseScript {

	private static final Logger log = Logger.getLogger(CreateCategoryTest.class.getName());

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("browser", BrowserType.CHROME);

		EventFiringWebDriver driver = getConfiguredDriver(log);
		GeneralActions actions = new GeneralActions(driver, log);

		actions.login("webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw");

		String categoryName = "new test category";
		actions.createCategory(categoryName);

		actions.checkCategoryInCategoriesTable(categoryName);

		driver.quit();
	}
}
