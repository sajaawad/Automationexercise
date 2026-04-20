package Test;


import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Products {

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
	
	
	@Test
	public void ProductsPage() throws InterruptedException {
		WebElement productBtn=driver.findElement(By.partialLinkText("Products"));
		productBtn.click();
		 
		
		//Verify user is navigated to ALL PRODUCTS page successfully
		Assert.assertEquals(driver.getCurrentUrl().contains("products"), true);
		
		
		
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,350)");
		
		Thread.sleep(3000);
		
		//7. Click on 'View Product' of first product
		WebElement viewDetails=driver.findElement(By.xpath("(//a[contains(text(),'View Product')])[1]"));
		viewDetails.click();
		
		
	}
	
	
	
	
}
