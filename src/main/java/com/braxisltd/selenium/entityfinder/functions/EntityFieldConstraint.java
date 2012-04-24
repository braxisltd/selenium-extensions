package com.braxisltd.selenium.entityfinder.functions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

public class EntityFieldConstraint implements EntityConstraint {
    private FieldConstraint fieldConstraint;

    public EntityFieldConstraint(FieldConstraint fieldConstraint) {
        this.fieldConstraint = fieldConstraint;
    }

    public boolean apply(@Nullable WebElement input) {
        for (WebElement candidateField : input.findElements(By.xpath("descendant::*"))) {
            if (fieldConstraint.apply(candidateField)) {
                return true;
            }
        }
        return false;
    }
}
