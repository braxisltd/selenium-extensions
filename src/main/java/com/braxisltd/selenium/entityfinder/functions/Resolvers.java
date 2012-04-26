package com.braxisltd.selenium.entityfinder.functions;

import org.openqa.selenium.WebElement;

public class Resolvers {
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

    public static FieldResolver<Boolean> checkableIsChecked() {
        return new FieldResolver<Boolean>() {
            public Boolean resolver(WebElement fieldElement) {
                return fieldElement.getAttribute("checked") != null;
            }
        };
    }
}
