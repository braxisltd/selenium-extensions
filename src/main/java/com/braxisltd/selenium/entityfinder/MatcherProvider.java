package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

public class MatcherProvider implements Predicate<WebElement> {
    private final EntityFinder.FinderContext finderContext;
    private Predicate<WebElement> condition;

    public MatcherProvider(EntityFinder.FinderContext finderContext) {
        this.finderContext = finderContext;
    }

    public Finder containsTest(final String text) {
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
