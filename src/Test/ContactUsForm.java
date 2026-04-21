package Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.testng.annotations.*;
 

//Test Case 6
public class ContactUsForm {
	WebDriver driver;
	String WebsiteName="https://automationexercise.com/";
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	String FirstName,Email,FullName;
	
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

	@Test(priority = 1,enabled = true)
	public void FillForm() throws SQLException, InterruptedException, IOException {
		WebElement contactUsBtn=driver.findElement(By.linkText("Contact us"));
		contactUsBtn.click();
		
		String query="select *, REPLACE(customerName + '@gmail.com' , ' ', '') AS Email   from customers where customerNumber = 103";
		rs=stmt.executeQuery(query);
		
		while(rs.next()) {	
			Email=rs.getString("Email");
			FullName=rs.getString("customerName");
		}
		
		WebElement nameTxt=driver.findElement(By.name("name"));
		nameTxt.sendKeys(FullName);
		
		WebElement emailTxt=driver.findElement(By.xpath("//input[@placeholder='Email']"));
		emailTxt.sendKeys(Email);
		
		WebElement subjectTxt=driver.findElement(By.name("subject"));
		subjectTxt.sendKeys("QA Testing");
		
		WebElement messageTxt=driver.findElement(By.id("message"));
		messageTxt.sendKeys("Hello , QA Testing team \nI'm here to talk about testing \nBest Regards saja");
		
		WebElement uploadFile=driver.findElement(By.name("upload_file"));
		
		
        String path = System.getProperty("user.home") + "/Downloads/test_upload.txt";

        File file = new File(path);
        
        if (file.exists()) {
        	file.delete();
        }

        FileWriter writer = new FileWriter(file);
        writer.write("This is automation test file");
        writer.close();
        
		uploadFile.sendKeys(path);
		
		WebElement submitBtn=driver.findElement(By.name("submit"));
		submitBtn.click();
		
		Thread.sleep(3000);
		
		//Click OK button
		driver.switchTo().alert().accept();
		
		//Verify success message 'Success! Your details have been submitted successfully.' is visible
		Assert.assertEquals(driver.getPageSource().contains("Success! Your details have been submitted successfully."), true);
		
		WebElement homeBtn=driver.findElement(By.linkText("Home"));
		homeBtn.click();
		
		
	}
}
