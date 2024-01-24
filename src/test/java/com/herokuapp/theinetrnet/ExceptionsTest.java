package com.herokuapp.theinetrnet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTest {

	private WebDriver driver;

	@Parameters("browser")
	@BeforeMethod
	private void setUp(@Optional("Chrome") String browser) {
		switch (browser) {
		case "Chrome":
			driver = new ChromeDriver();

			break;
		case "Firefox":
			driver = new FirefoxDriver();

			break;

		default:
			driver = new ChromeDriver();
			break;
		}

		System.out.println("Browser initiated");

//		open test page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		driver.manage().window().maximize();

	}

	@Test
	public void verifyNoSuchElementException() {

		WebElement addBtn = driver.findElement(By.id("add_btn"));
		addBtn.click();
		
		//Add explicit wait 
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement rowinputField=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

		// Verify Row2 input field is displayed after clicking add button

		

		Assert.assertTrue(rowinputField.isDisplayed(), "Row2 input field is not displayed");

	}
	
	@Test
	public void invalidElementStateExceptionTest()
	{
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement inputField1= driver.findElement(By.xpath("//div[@id='row1']/input"));
		WebElement editBtn= driver.findElement(By.id("edit_btn"));
		editBtn.click();
		inputField1.clear();
		inputField1.sendKeys("Dosa");
		WebElement saveBtn= driver.findElement(By.id("save_btn"));
		saveBtn.click();
		String value=inputField1.getAttribute("value");
		Assert.assertEquals(value,"Dosa", "Input1 Field value is not as expected");
		
		WebElement confirmationMsg= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation"))); 
		String msgText=confirmationMsg.getText();
		System.out.println("Confirmation message is:" + msgText); 
		Assert.assertEquals(msgText, "Row 1 was saved", "Confirmation message is not as expected." );
	}

	@AfterMethod
	private void tearDown() {

		driver.close();
	}

}
