package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverPool {
    private static WebDriver driver;


    public static WebDriver setUpDriver(DriverParameters driverParameters) {
        if (driver == null) {
            switch (driverParameters.getPlatform().toLowerCase()) {
                case "chrome":
                    driver = setUpChromeDriver(driverParameters);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported platform: " + driverParameters.getPlatform());
            }
        }
        return driver;
    }

    private static WebDriver setUpChromeDriver(DriverParameters driverParameters) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(driverParameters.getChromeArguments());

        if (driverParameters.getHeadless() != null && driverParameters.getHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
        }

        return new ChromeDriver(options);
    }

//    private static WebDriver setUpFirefoxDriver(DriverParameters driverParameters) {
//        FirefoxOptions options = new FirefoxOptions();
//
//        if (driverParameters.getHeadless() != null && driverParameters.getHeadless()) {
//            options.addArguments("--headless");
//        }
//
//        return new FirefoxDriver(options);
//    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call setUpDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

