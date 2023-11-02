import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.GroupLayout.Group;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestCases {

	WebDriver driver = new ChromeDriver();
	String Url = "https://global.almosafer.com/ar";
	Random rand = new Random();
	String urlApi = "https://www.almosafer.com/ar/hotels/Dubai/13-11-2023/14-11-2023/2_adult?placeId=ChIJRcbZaklDXz4RYlEphFBu5r0&city=%D8%AF%D8%A8%D9%8A&sortBy=LOWEST_PRICE";

	@BeforeTest
	public void mySetup() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.manage().window().maximize();
		int RandomURLINDEX = rand.nextInt(2);
		String[] myURLS = { "https://global.almosafer.com/ar", "https://global.almosafer.com/en" };

		driver.get(myURLS[RandomURLINDEX]);

		if (driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div")).isDisplayed()) {
			driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div/button[1]")).click();

		}

	}

	@Test(groups = "mid", enabled = false)
	public void CheckTheLanguage() {

		if (driver.getCurrentUrl().contains("ar")) {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");

			Assert.assertEquals(ActualLanguage, "ar");

		} else {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			System.out.println(ActualLanguage);
			Assert.assertEquals(ActualLanguage, "en");

		}
	}

	@Test(groups = "low", enabled = false)
	public void checkTheCurrency() {

		WebElement CurrencyTab = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/div[1]/div/button"));
		String ActualCurrency = CurrencyTab.getText();
		Assert.assertEquals(ActualCurrency, "SAR");
	}

	@Test(enabled = false)
	public void TestTheHotelTab() {

		String[] EnglishCountries = { "Dubai", "Jeddah", "Riyadh" };
		int RandomEnglishINDEX = rand.nextInt(3);

		String[] ArabicCountries = { "جدة", "دبي" };
		int RandomArabicIndex = rand.nextInt(2);

		if (driver.getCurrentUrl().contains("ar")) {
			WebElement HotelTab = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]"));
			HotelTab.click();

			WebElement SearchBar = driver.findElement(By.xpath(
					"//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[1]/div/div/div/div/input"));
			SearchBar.sendKeys(ArabicCountries[RandomArabicIndex] + Keys.ARROW_DOWN + Keys.ENTER);

			WebElement SearchButton = driver.findElement(
					By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[4]/button"));
			SearchButton.click();

			WebElement MySelector = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));

			Select selector = new Select(MySelector);
			selector.selectByIndex(rand.nextInt(2));

		} else {

			WebElement HotelTab = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]"));
			HotelTab.click();

			WebElement SearchBar = driver.findElement(By.xpath(
					"//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[1]/div/div/div/div/input"));
			SearchBar.sendKeys(EnglishCountries[RandomEnglishINDEX] + Keys.ARROW_DOWN + Keys.ENTER);

			WebElement SearchButton = driver.findElement(
					By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[4]/button"));
			SearchButton.click();

			WebElement MySelector = driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb"));

			Select selector = new Select(MySelector);
			selector.selectByIndex(rand.nextInt(2));

		}

	}

	@Test(enabled = false)
	public void checkDefaultFlightDates() {

		WebElement departureDateElement = driver.findElement(By.xpath(
				"//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[3]/div/div/div/div[1]/span[2]"));
		String departureDateText = departureDateElement.getText();

		// Find the return date WebElement and extract the text
		WebElement returnDateElement = driver.findElement(By.xpath(
				"//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[3]/div/div/div/div[2]/span[2]"));
		String returnDateText = returnDateElement.getText();

		// Calculate the expected departure and return dates
		LocalDate currentDate = LocalDate.now();
		LocalDate expectedDepartureDate = currentDate.plusDays(1);
		LocalDate expectedReturnDate = currentDate.plusDays(2);

		// Format the expected dates as strings
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");
		String expectedDepartureDateString = expectedDepartureDate.format(dateFormatter);
		String expectedReturnDateString = expectedReturnDate.format(dateFormatter);

		System.out.println("Expected Departure Date: " + expectedDepartureDateString);
		System.out.println("Actual Departure Date: " + departureDateText);
		System.out.println("Expected Return Date: " + expectedReturnDateString);
		System.out.println("Actual Return Date: " + returnDateText);

		// Verify that the extracted dates match the expected dates
		Assert.assertEquals(departureDateText, expectedDepartureDateString, "Departure date mismatch");
		Assert.assertEquals(returnDateText, expectedReturnDateString, "Return date mismatch");

	}

	@Test(groups = "high", enabled = false)
	public void checkTheContactNumber() {
		WebElement ContactNumberTab = driver
				.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/a[2]/strong"));
		String ActualContactNumber = ContactNumberTab.getText();
		Assert.assertEquals(ActualContactNumber, "+966554400000");
	}

	@Test(enabled = false)
	public void LowestPrice() throws InterruptedException {

		driver.get(urlApi);
		Thread.sleep(8000);

		WebElement Container = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]"));

		List<WebElement> Prices = Container.findElements(By.className("Price__Value"));

		for (int i = 0; i < Prices.size(); i++) {
			String FirstPrice = Prices.get(0).getText();
			String LastPrice = Prices.get(Prices.size() - 1).getText();

			int FirstPriceAsNumber = Integer.parseInt(FirstPrice);
			int LastPriceAsNumber = Integer.parseInt(LastPrice);

			System.out.println(FirstPrice);
			System.out.println(LastPrice);

			Assert.assertEquals(FirstPriceAsNumber < LastPriceAsNumber, true);

			WebElement LowestPriceButton = driver
					.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div[1]/div[2]/section[1]/div/button[2]"));
			Assert.assertEquals(LowestPriceButton.isEnabled(), true);

		}

	}

	@Test()
	public void SearchingProcess() {

		WebElement SearchingResult = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/section/span"));
		String TextSearchingResult = SearchingResult.getText();
		System.out.println(TextSearchingResult);
		boolean SearchingProcessResult = TextSearchingResult.contains("founds")
				|| TextSearchingResult.contains("وجدنا");
		Assert.assertEquals(SearchingProcessResult, true);

	}

	@AfterTest
	public void postTesting() {
	}
}