package base;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base 
{

	public static WebDriver driver;	
	public static ExtentSparkReporter sparkreport;
	public static ExtentReports report;
	public static ExtentTest test;

	public static int count=1;
	
	@BeforeSuite
	public void reportInitialize()
	{
		sparkreport=new ExtentSparkReporter("./Reports/flipkart.html");
		sparkreport.config().setReportName("Flipkart Functional Testing");
		
		report=new ExtentReports();
		report.attachReporter(sparkreport);
		report.setSystemInfo("ApplicationName","Flipkart.com");
		report.setSystemInfo("Tester","rama");
		report.setSystemInfo("Environment","TestEnv");
	}

	@BeforeTest
	@Parameters({"browser"})
	public void setUp(String br)
	{
		System.out.println("setup is called");
		if(br.matches("firefox"))
		{
			driver=new FirefoxDriver();
			test=report.createTest("Browser : Firefox");
			test.log(Status.INFO,"");
		}
		if(br.matches("chrome"))
		{
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--remote-allow-origins=*");        
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver(option);
			test=report.createTest("Browser : Chrome");
			test.log(Status.INFO,"");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	public void openUrl(String url)
	{
		driver.get(url);
	}
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	
	@AfterSuite
	public void saveReport()
	{
		report.flush();  //save the report.
	}
	public void takeScreenshot(String path)
	{
		try {
		File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f,new File("./Reports/images/"+path));
		test.addScreenCaptureFromPath("./Reports/images/"+path);}catch(Exception e) {}
	}
}
