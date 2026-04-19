package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Login {
	WebDriver driver;
	String WebsiteName="https://automationexercise.com/";
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	String FirstName,LastName,Company , Address ,Country, State ,City, Zipcode ,MobileNumber,Email,FullName;
	
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

	
	//Test Case 2
	@Test(priority = 1,enabled = true)
	public void LoginUser() throws SQLException {
		WebElement loginBtn=driver.findElement(By.linkText("Signup / Login"));
		loginBtn.click();
		
		String query="select *, REPLACE(customerName + '@gmail.com' , ' ', '') AS Email   from customers where customerNumber = 103";
		rs=stmt.executeQuery(query);
		
		while(rs.next()) {	
			Email=rs.getString("Email");
			FullName=rs.getString("customerName");
		}
		
		WebElement fillEmail=driver.findElement(By.xpath("//input[@data-qa='login-email']"));
		fillEmail.sendKeys(Email);
		
		WebElement fillpassword=driver.findElement(By.name("password"));
		fillpassword.sendKeys("123");
		
		//Click 'Login' button
		WebElement loginBtn1=driver.findElement(By.xpath("//button[@data-qa='login-button']"));
		loginBtn1.click();
		
		
		String expectedText="Logged in as " +  FullName;
		System.out.println(expectedText);
		//WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Logged in as')]"));
		//String text = element.getText();		
		//Assert.assertTrue(text.contains(expectedText));
		
		
		Assert.assertEquals(driver.getPageSource().contains("Logged in as "), true);
		Assert.assertEquals(driver.getPageSource().contains(FullName), true);
		
	}
	
	@Test(priority = 2 ,enabled = false)
	public void DeleteAccount() {
		WebElement deleteAccBtn=driver.findElement(By.linkText("Delete Account"));
		deleteAccBtn.click();
		
		//18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
		Boolean accountDeletedVisibility=driver.findElement(By.xpath("//b[normalize-space()='Account Deleted!']")).isDisplayed();
		Assert.assertEquals(accountDeletedVisibility, true);
	
		WebElement continueBtn=driver.findElement(By.className("btn-primary"));
		continueBtn.click();
		
	}
	
	
	//Test Case 3 
	@Test(priority = 1 ,enabled = false)
	public void IncorrectLogin() {
		
		WebElement loginBtn=driver.findElement(By.linkText("Signup / Login"));
		loginBtn.click();
		
		WebElement fillEmail=driver.findElement(By.xpath("//input[@data-qa='login-email']"));
		//fillEmail.sendKeys("sdfdgdgdgd"); // please include @ in email address is shown
		fillEmail.sendKeys("test@gmail.com");
		
		
		WebElement fillpassword=driver.findElement(By.name("password"));
		fillpassword.sendKeys("123");
		
		//Click 'Login' button
		WebElement loginBtn1=driver.findElement(By.xpath("//button[@data-qa='login-button']"));
		loginBtn1.click();
		
		
		
		Assert.assertEquals(driver.getPageSource().contains("Your email or password is incorrect"), true);
		
	}
	
	@Test(priority = 2,enabled = true)
	public void Logout() {
		WebElement logoutBtn=driver.findElement(By.linkText("Logout"));
		logoutBtn.click();
		
		//10. Verify that user is navigated to login page
		Assert.assertEquals(driver.getCurrentUrl().contains("login"), true);
		
	}
}
