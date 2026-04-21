package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Subscription {
	
	WebDriver driver;
	String WebsiteName="https://automationexercise.com/";
	 
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	String Email,FullName;
	
	Random rand=new Random();
	
	@BeforeTest
	public void Setup() {
		driver =new ChromeDriver();
		driver.get(WebsiteName);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		try {
			ConnectDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@BeforeMethod
	public void RunAfterEveryMethod() throws SQLException {
		stmt=conn.createStatement();
	}
	@Test
	public void ConnectDB() throws SQLException {
		String url = "jdbc:sqlserver://DESKTOP-PVL83NM\\MSSQLSERVER2018;databaseName=classicmodels;encrypt=true;trustServerCertificate=true";

		String user = "sarh";
		String password = "sarh123";

		conn=DriverManager.getConnection(url,user,password);
		
	}

	
	//Test Case 10
	@Test(priority = 1,enabled = true)
	public void HomePageSubscription() throws SQLException {
		
		//4. Scroll down to footer
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		
		//5. Verify text 'SUBSCRIPTION'
		Assert.assertEquals(driver.getPageSource().contains("Subscription"), true);
		
		String query="select *, REPLACE(customerName + '@gmail.com' , ' ', '') AS Email   from customers where customerNumber = 103";
		rs=stmt.executeQuery(query);
		
		while(rs.next()) {	
			Email=rs.getString("Email");
		}
		
		WebElement fillEmail=driver.findElement(By.id("susbscribe_email"));
		fillEmail.sendKeys(Email);
		
		WebElement subscribeBtn=driver.findElement(By.id("subscribe"));
		subscribeBtn.click();
		
		
		//7. Verify success message 'You have been successfully subscribed!' is visible
		Assert.assertEquals(driver.getPageSource().contains("You have been successfully subscribed!"), true);
		
		
	}
	
	//Test Case 11
	@Test(priority = 2,enabled = true)
	public void CartPageSubscription() throws SQLException {
		WebElement cartBtn=driver.findElement(By.linkText("Cart"));
		cartBtn.click();
		
		//Scroll down to footer
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		
		//Verify text 'SUBSCRIPTION'
		Assert.assertEquals(driver.getPageSource().contains("Subscription"), true);
		
		String query="select *, REPLACE(customerName + '@gmail.com' , ' ', '') AS Email   from customers where customerNumber = 103";
		rs=stmt.executeQuery(query);
		
		while(rs.next()) {	
			Email=rs.getString("Email");
		}
		
		WebElement fillEmail=driver.findElement(By.id("susbscribe_email"));
		fillEmail.sendKeys(Email);
		
		WebElement subscribeBtn=driver.findElement(By.id("subscribe"));
		subscribeBtn.click();
		
		
		//Verify success message 'You have been successfully subscribed!' is visible
		Assert.assertEquals(driver.getPageSource().contains("You have been successfully subscribed!"), true);
		
		
	}

}
