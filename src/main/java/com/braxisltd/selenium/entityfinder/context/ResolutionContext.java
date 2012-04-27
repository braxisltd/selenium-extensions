package com.braxisltd.selenium.entityfinder.context;

import com.braxisltd.selenium.entityfinder.functions.Action;
import com.braxisltd.selenium.entityfinder.functions.FieldResolver;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.openqa.selenium.WebElement;

public class ResolutionContext {
    protected Optional<WebElement> fieldElement;

    public <T> T resolve(FieldResolver<T> resolver) {
        checkState();
        return resolver.resolver(fieldElement.get());
    }

    public void doAction(Action action) {
        checkState();
        action.doAction(fieldElement.get());
    }

    private void checkState() {
        Preconditions.checkState(fieldElement != null, "Cannot resolve content from a query object");
        Preconditions.checkState(fieldElement.isPresent(), "Unable to resolve web element");
    }
}
