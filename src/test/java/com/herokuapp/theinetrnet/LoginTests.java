package com.herokuapp.theinetrnet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

	private WebDriver driver;

	@Parameters("browser")
	@BeforeMethod
	private void setUp(@Optional("Chrome") String browser) {
		switch (browser) {
		case "Chrome":
			driver= new ChromeDriver(); 
			
			break;
		case "Firefox":
			driver= new FirefoxDriver(); 
			
			break;

		default:
			driver= new ChromeDriver();
			break;
		}

		sleep(1);
		System.out.println("Browser initiated");

//		open test page
		String url = "https://the-internet.herokuapp.com/";
		driver.get(url);
		driver.manage().window().maximize();
		sleep(1);
	}

	@Test
	public void positiveLoginTest() {
		System.out.println("Positive Login Test started");

		driver.findElement(By.linkText("Form Authentication")).click();
		System.out.println("Test Application opened");

//		enter user and password 
		WebElement user = driver.findElement(By.id("username"));
		WebElement pwd = driver.findElement(By.cssSelector("input#password"));
		user.sendKeys("tomsmith");
		pwd.sendKeys("SuperSecretPassword!");

//		click login
		WebElement loginBtn = driver.findElement(By.cssSelector("button.radius"));
		loginBtn.click();

//Urls verification
		String expUrl = "https://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expUrl, "Actual page urls is not same as expected url");
		System.out.println("Verified home page url successfully");

// Logout button Verification
		WebElement logoutBtn = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logoutBtn.isDisplayed(), "Logout button is not visible");
		System.out.println("Logout button is visible");

//Login success msg verification
		WebElement successMsg = driver.findElement(By.id("flash"));
		String actualMsg = successMsg.getText();
		String expectedMsg = "You logged into a secure area!";
		// Assert.assertEquals(actualMsg, expectedMsg, "Actual meaasge is not same as
		// Expected message");
		Assert.assertTrue(actualMsg.contains(expectedMsg), "Actual meaasge is not same as Expected message");
		System.out.println("Verified successful login message");

	}

	@Parameters({ "username", "password", "errorMsg" })
	@Test
	public void negativeLoginTest(String username, String password, String errMsg) {

		WebElement loginForm = driver.findElement(By.linkText("Form Authentication"));
		loginForm.click();

		WebElement user = driver.findElement(By.id("username"));
		WebElement pwd = driver.findElement(By.cssSelector("input#password"));
		user.sendKeys(username);
		pwd.sendKeys(password);
		sleep(1);
		WebElement loginBtn = driver.findElement(By.cssSelector("button.radius"));
		loginBtn.click();
		sleep(1);

		// Wrong user name verification message
		WebElement errorMessage = driver.findElement(By.id("flash"));
		String actualErrorMsg = errorMessage.getText();
		String expectedErrorMsg = errMsg;
		// Assert.assertEquals(actualErrorMsg.contains(expectedErrorMsg),expectedErrorMsg,
		// "Invalid error message on enetring incorrect username");
		Assert.assertTrue(actualErrorMsg.contains(expectedErrorMsg),
				"Actual error message is not same as expected error message");

	}

	@AfterMethod
	private void tearDown() {

		driver.close();
	}

	/**
	 * Adds stop time between execution
	 * 
	 */
	private void sleep(int m) {
		try {
			Thread.sleep(m * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
