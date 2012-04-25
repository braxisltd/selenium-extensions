package com.braxisltd.selenium.entityfinder;

import org.openqa.selenium.WebElement;

public class Resolutions {
    public static FieldResolver<String> text() {
        return new FieldResolver<String>() {
            public String resolver(WebElement fieldElement) {
                return fieldElement.getText();
            }
        };
    }

    public static FieldResolver<String> attribute(final String name) {
        return new FieldResolver<String>() {
            public String resolver(WebElement fieldElement) {
                return fieldElement.getAttribute(name);
            }
        };
    }

    public static FieldResolver<String> id() {
        return attribute("id");
    }
}
