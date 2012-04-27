package com.braxisltd.selenium.entityfinder.functions;

import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

public class FieldMatchers {
    public static FieldConstraint checkable(final boolean checked) {
        return new FieldConstraint() {
            public boolean apply(WebElement input) {
                boolean isInput = input.getTagName().equals("input");
                String checkedAttribute = input.getAttribute("checked");
                return isInput && checked == (checkedAttribute != null);
            }
        };
    }

    public static FieldConstraint attribute(final String name, final String value) {
        return new FieldConstraint() {
            public boolean apply(@Nullable WebElement input) {
                return value.equals(input.getAttribute(name));
            }
        };
    }
}
