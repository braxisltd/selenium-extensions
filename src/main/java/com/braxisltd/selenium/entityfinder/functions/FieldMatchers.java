package com.braxisltd.selenium.entityfinder.functions;

import com.braxisltd.selenium.entityfinder.functions.FieldConstraint;
import org.openqa.selenium.WebElement;

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
}
