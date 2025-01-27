package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestInitChrome extends TestInit {

    @Override
    public WebDriver beforeTest(DriverParameters driverParameters) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1920,1080");
        if (driverParameters.getHeadless() != null && driverParameters.getHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
        }
        WebDriverManager.chromedriver().setup();

        return createLocalChromeDriver(options);
    }

    private ChromeDriver createLocalChromeDriver(ChromeOptions options) {
       return new ChromeDriver(options);
    }

}
