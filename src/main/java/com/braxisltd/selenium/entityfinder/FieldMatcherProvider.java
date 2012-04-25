package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

public class FieldMatcherProvider<T> implements Predicate<WebElement> {
    private final EntityFinder.FinderContext finderContext;
    private Predicate<WebElement> condition = Predicates.alwaysTrue();

    public FieldMatcherProvider(EntityFinder.FinderContext finderContext) {
        this.finderContext = finderContext;
    }

    public Finder<T> containsTest(final String text) {
        condition = new Predicate<WebElement>() {
            public boolean apply(@Nullable WebElement input) {
                return input.getText().contains(text);
            }
        };
        return finderContext;
    }

    public boolean apply(@Nullable WebElement input) {
        return condition.apply(input);
    }
}
