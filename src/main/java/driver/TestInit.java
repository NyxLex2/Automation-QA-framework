package driver;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

public abstract class TestInit {
    public abstract WebDriver beforeTest(DriverParameters driverParameters) throws IOException, InterruptedException;
}