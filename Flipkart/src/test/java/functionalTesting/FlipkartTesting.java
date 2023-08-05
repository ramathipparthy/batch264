package functionalTesting;


import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import base.Base;
import pages.HomePage;
import pages.ProductsListPage;
import pages.SingleProductPage;

public class FlipkartTesting extends Base
{

	String valuetoSearch="mobiles";
	
	@Test
	public void verifyTitleandHeading()
	{		
		
		HomePage homepage=new HomePage();
		ProductsListPage productslistpage=new ProductsListPage();
		homepage.openUrl("http://www.flipkart.com");
		homepage.closeLogin();
		homepage.search(valuetoSearch);
		String title=productslistpage.getTitle().toLowerCase();
		String heading=productslistpage.getHeading().toLowerCase();		
		test=report.createTest("Validate Title and Heading");
		if(title.contains(valuetoSearch) && heading.matches(valuetoSearch))
		{		
			test.log(Status.PASS, "Title and heading are matching as expected");
			takeScreenshot("Heading_"+count+".png");
		}			
		else
		{			
			test.log(Status.FAIL, "Title or heading NOT matching as expected");
			takeScreenshot("Heading_"+count+".png");
		}
		count++;			
	}
	@Test
	public void verifyPricesSorted()
	{
				
		HomePage homepage=new HomePage();
		ProductsListPage productslistpage=new ProductsListPage();
		
		homepage.openUrl("http://www.flipkart.com");
		homepage.closeLogin();
		homepage.search(valuetoSearch);
		productslistpage.clickLowToHigh();
		List<Integer> actprices= productslistpage.getPrices();
		List<Integer> expprices=actprices.stream().sorted().collect(Collectors.toList());
		test=report.createTest("Validate prices are sorted");
		if(actprices.equals(expprices))
		{
			test.log(Status.PASS, "All prices are in sorting order as expected "+actprices);
			takeScreenshot("Prices_"+count+".png");
		}
		else
		{
			test.log(Status.FAIL, "All prices are NOT sorting order as expected "+actprices);
			takeScreenshot("Prices_"+count+".png");
		}
		count++;
		
	}
	@Test
	public void verifyProductNameandPrice()
	{
		HomePage homepage=new HomePage();
		ProductsListPage productslistpage=new ProductsListPage();
		SingleProductPage singleproductpage=new SingleProductPage();	
		
		homepage.openUrl("http://www.flipkart.com");
		homepage.closeLogin();
		homepage.search(valuetoSearch);
		String first[]=productslistpage.clickOneProduct(8);
		String second[]=singleproductpage.getProductdetails();
		test=report.createTest("Validate ProductName & Price");
		if(second[0].contains(first[0])&& first[1].matches(second[1]))
		{
			test.log(Status.PASS,"Product name and price are matched in both pages");
			takeScreenshot("ProductName_Price_"+count+".png");
		}
		else
		{
			test.log(Status.FAIL,"Product name or price NOT matched in both pages");
			takeScreenshot("ProductName_Price_"+count+".png");
		}
		count++;
		
		
	}
	
}
