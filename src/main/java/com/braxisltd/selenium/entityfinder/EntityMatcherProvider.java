package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.functions.EntityConstraint;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

public class EntityMatcherProvider<T> implements EntityConstraint {
    private Finder<T> finder;
    private Predicate<WebElement> constraint = Predicates.alwaysTrue();

    public EntityMatcherProvider(Finder<T> finder) {
        this.finder = finder;
    }

    public Finder hasId(final String id) {
        constraint = new EntityConstraint() {
            public boolean apply(@Nullable WebElement input) {
                String idAttribute = input.getAttribute("id");
                return idAttribute != null && idAttribute.equals(id);
            }
        };
        return finder;
    }

    public boolean apply(WebElement input) {
        return constraint.apply(input);
    }
}
