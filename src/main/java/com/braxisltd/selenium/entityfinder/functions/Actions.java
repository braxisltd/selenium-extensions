package com.braxisltd.selenium.entityfinder.functions;

import org.openqa.selenium.WebElement;

public class Actions {
    public static Action click() {
        return new Action() {
            public void doAction(WebElement element) {
                element.click();
            }
        };
    }
}
