package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.openqa.selenium.WebElement;

public class ResolutionContext {
    protected Optional<WebElement> fieldElement;

    public <T> T resolve(FieldResolver<T> resolver) {
        Preconditions.checkState(fieldElement != null, "Cannot resolve content from a query object");
        Preconditions.checkState(fieldElement.isPresent(), "Unable to resolve web element");
        return resolver.resolver(fieldElement.get());
    }
}
