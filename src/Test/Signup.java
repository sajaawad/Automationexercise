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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class Signup {
	
	
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

	
	//Test Case 1 
	@Test(priority = 1)
	public void RegisterUser() throws SQLException {
		WebElement signupBtn=driver.findElement(By.linkText("Signup / Login"));
		signupBtn.click();
		
		String query="select *, REPLACE(customerName + '@gmail.com' , ' ', '') AS Email   from customers where customerNumber = 103";
		rs=stmt.executeQuery(query);
		
		while(rs.next()) {
			FirstName=rs.getString("contactFirstName");
			MobileNumber=rs.getString("phone");
			LastName=rs.getString("contactLastName");
			Address=rs.getString("addressLine1");
			Email=rs.getString("Email");
			FullName=rs.getString("customerName");
			State=rs.getString("state");
			City=rs.getString("city");
			Zipcode=rs.getString("postalCode");
			Company="QA Testing Company";
		}
		
		//Verify 'New User Signup!' is visible
		Boolean newUserSignupVisibility=driver.findElement(By.xpath("//h2[normalize-space()='New User Signup!']")).isDisplayed();
		Assert.assertEquals(newUserSignupVisibility, true);
		
		
		//Enter name and email address
		WebElement fillName=driver.findElement(By.name("name"));
		fillName.sendKeys(FullName);
		
		WebElement fillEmail=driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
		fillEmail.sendKeys(Email);
		
		//Click 'Signup' button
		WebElement signupBtn1=driver.findElement(By.xpath("//button[@data-qa='signup-button']"));
		signupBtn1.click();
		
		//Verify that 'ENTER ACCOUNT INFORMATION' is visible
		Boolean EnterAccountInformationVisibility=driver.findElement(By.xpath("//b[normalize-space()='Enter Account Information']")).isDisplayed();
		Assert.assertEquals(EnterAccountInformationVisibility, true);
	}
	
	
	@Test(priority = 2)
	public void FillAccountInformation() {
		//9. Fill details: Title, Name, Email, Password, Date of birth
		WebElement title = driver.findElement(By.id("id_gender2"));
		title.click();
		
		WebElement passwordTxt=driver.findElement(By.id("password"));
		passwordTxt.sendKeys("123");
		
		WebElement dayList=driver.findElement(By.id("days"));
		Select Day=new Select(dayList);
		
		int size=dayList.findElements(By.tagName("option")).size();
		
		int dayIndex=rand.nextInt(1,size);
		Day.selectByIndex(dayIndex);
		
	
		
		WebElement monthList=driver.findElement(By.id("months"));
		Select Month=new Select(monthList);		
		int monthSize=monthList.findElements(By.tagName("option")).size();
		int monthIndex=rand.nextInt(1,monthSize);
		Month.selectByIndex(monthIndex);		
		
		WebElement yearList=driver.findElement(By.id("years"));
		Select Year=new Select(yearList);		
		int yearSize=yearList.findElements(By.tagName("option")).size();		
		int yearIndex=rand.nextInt(1,yearSize);
		Year.selectByIndex(yearIndex);
		
		WebElement newsletterChk=driver.findElement(By.id("newsletter"));
		newsletterChk.click();
		
		WebElement optinChk=driver.findElement(By.id("optin"));
		optinChk.click();
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,700)");
		

		WebElement first_name=driver.findElement(By.id("first_name"));
		first_name.sendKeys(FirstName);
		
		WebElement last_name=driver.findElement(By.id("last_name"));
		last_name.sendKeys(LastName);
		
		WebElement companyTxt=driver.findElement(By.id("company"));
		companyTxt.sendKeys(Company);
		
		WebElement address1Txt=driver.findElement(By.id("address1"));
		address1Txt.sendKeys(Address);
		
		WebElement address2Txt=driver.findElement(By.id("address2"));
		address2Txt.sendKeys(Address + "2");
		
		WebElement country=driver.findElement(By.id("country"));
		Select CountryList=new Select(country);
		
		int countrySize=country.findElements(By.tagName("option")).size();
		int countryIndex=rand.nextInt(countrySize);
		CountryList.selectByIndex(countryIndex);
		
		WebElement state=driver.findElement(By.id("state"));
		state.sendKeys(State != null ? State : "N/A");
		
		WebElement city=driver.findElement(By.id("city"));
		city.sendKeys(City);
		
		WebElement zipcode=driver.findElement(By.id("zipcode"));
		zipcode.sendKeys(Zipcode);
		
		WebElement mobile_number=driver.findElement(By.id("mobile_number"));
		mobile_number.sendKeys(MobileNumber);
		
		WebElement CreateAccountBtn=driver.findElement(By.xpath("//button[@data-qa='create-account']"));
		CreateAccountBtn.click();
		
		// Verify that 'ACCOUNT CREATED!' is visible
		Boolean accountCreatedVisibility=driver.findElement(By.xpath("//b[normalize-space()='Account Created!']")).isDisplayed();
		Assert.assertEquals(accountCreatedVisibility, true);
	
		WebElement continueBtn=driver.findElement(By.className("btn-primary"));
		continueBtn.click();
		
		//16. Verify that 'Logged in as username' is visible
		String expectedText="Logged in as " +  FullName;
		System.out.println(expectedText);
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Logged in as')]"));
		String text = element.getText();		
		Assert.assertTrue(text.contains(expectedText));
				
	}
	
	@Test(priority = 3 ,enabled = false)
	public void DeleteAccount() {
		WebElement deleteAccBtn=driver.findElement(By.linkText("Delete Account"));
		deleteAccBtn.click();
		
		//18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
		Boolean accountDeletedVisibility=driver.findElement(By.xpath("//b[normalize-space()='Account Deleted!']")).isDisplayed();
		Assert.assertEquals(accountDeletedVisibility, true);
	
		WebElement continueBtn=driver.findElement(By.className("btn-primary"));
		continueBtn.click();
		
	}
	
	@Test(priority = 3,enabled = true)
	public void Logout() {
		WebElement logoutBtn=driver.findElement(By.linkText("Logout"));
		logoutBtn.click();
		
		//10. Verify that user is navigated to login page
		Assert.assertEquals(driver.getCurrentUrl().contains("login"), true);
		
	}
	
	//Test Case 5
	@Test(priority = 4 ,enabled = true)
	public void DuplicateEmail() {
		//Enter name and email address
		WebElement fillName=driver.findElement(By.name("name"));
		fillName.sendKeys(FullName);
		
		WebElement fillEmail=driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
		fillEmail.sendKeys(Email);
		
		//Click 'Signup' button
		WebElement signupBtn1=driver.findElement(By.xpath("//button[@data-qa='signup-button']"));
		signupBtn1.click();
				
		Assert.assertEquals(driver.getPageSource().contains("Email Address already exist"), true);
	}
	
}
