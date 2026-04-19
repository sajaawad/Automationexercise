package Test;


import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


//Test Case 7
public class TestCases {
	WebDriver driver;
	String WebsiteName="https://automationexercise.com/";
	
	
	String FirstName,Email,FullName;
	
	Random rand=new Random();
	
	@BeforeTest
	public void Setup() {
		driver =new ChromeDriver();
		driver.get(WebsiteName);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}
	
	@Test(priority = 1)
	public void TestCasePage() {
		WebElement testCaseBtn=driver.findElement(By.linkText("Test Cases"));
		testCaseBtn.click();
		
		Assert.assertEquals(driver.getCurrentUrl().contains("test_cases"), true);
	}
}
