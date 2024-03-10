import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.*;

public class RedBus {

	public static WebDriver driver;
	public static List<String> allWeekEndDates = new ArrayList<String>();

	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.get("https://www.redbus.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		List<String> weekEndDates = getWeekEndDates("Dec 2024");
		System.out.println(weekEndDates);
		driver.quit();
	}

	public static List<String> getWeekEndDates(String monthAndYear) {
		driver.findElement(By.cssSelector("div.labelCalendarContainer")).click();
		WebElement monthYearNumberOfHolidays = driver.findElement(By.cssSelector("div[class^='DayNavigator__IconBlock'][style*='font-size']"));
		while (!monthYearNumberOfHolidays.getText().contains(monthAndYear)) {
			System.out.println(monthYearNumberOfHolidays.getText());
			driver.findElement(By.cssSelector("div[class^='DayNavigator__IconBlock']:last-child>svg")).click();
		}

		System.out.println(monthYearNumberOfHolidays.getText());

		List<WebElement> dates = driver.findElements(By.cssSelector("span[class^='DayTiles__CalendarDaysSpan']"));
		for (WebElement date : dates) {
			String col = date.getCssValue("color");
			String bcol = date.getCssValue("background-color");
			if (bcol.equals("rgba(239, 67, 72, 1)") || col.equals("rgba(216, 78, 85, 1)")) {
				String weekEndDate = date.getText();
				allWeekEndDates.add(weekEndDate);
			}
		}
		return allWeekEndDates;
	}

}
