package com.braxisltd.selenium.entityfinder.functions;

import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

public class FieldConstraintCondition implements FieldConstraint {

    private final FieldConstraint condition;

    private FieldConstraintCondition(FieldConstraint condition) {
        this.condition = condition;
    }

    public static FieldConstraintCondition text(final String text) {
        return new FieldConstraintCondition(new FieldConstraint() {
            public boolean apply(@Nullable WebElement input) {
                return input.getText().contains(text);
            }
        });
    }

    public boolean apply(@Nullable WebElement input) {
        return condition.apply(input);
    }
}
