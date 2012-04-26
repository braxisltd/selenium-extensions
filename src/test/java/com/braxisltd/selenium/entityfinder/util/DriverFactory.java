package com.braxisltd.selenium.entityfinder.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DriverFactory {
    public static WebDriver create() {
        WebDriver driver = new HtmlUnitDriver();
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }
}
