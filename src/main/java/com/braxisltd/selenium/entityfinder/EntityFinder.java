package com.braxisltd.selenium.entityfinder;

import org.openqa.selenium.WebDriver;
import sun.misc.RegexpTarget;

public class EntityFinder {
    private WebDriver driver;

    public EntityFinder(WebDriver driver) {
        this.driver = driver;
    }

    public FindingContext find(String entityClassName) {
        return new FindingContext(driver, entityClassName);
    }
}
