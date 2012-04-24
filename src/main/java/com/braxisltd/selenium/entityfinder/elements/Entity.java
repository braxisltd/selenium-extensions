package com.braxisltd.selenium.entityfinder.elements;

import com.braxisltd.selenium.entityfinder.functions.EntityConstraint;
import com.google.common.base.Predicates;
import org.openqa.selenium.WebElement;

public class Entity {

    private final WebElement webElement;

    public Entity(WebElement webElement) {
        this.webElement = webElement;
    }

    public boolean matchesCriteria(Iterable<EntityConstraint> criterion) {
        return Predicates.and(criterion).apply(webElement);
    }
}
