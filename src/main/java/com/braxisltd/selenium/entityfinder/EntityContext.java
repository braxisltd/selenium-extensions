package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Optional;
import org.openqa.selenium.WebElement;

public class EntityContext extends ResolutionContext {

    public EntityContext(WebElement element) {
        fieldElement = Optional.of(element);
    }
}
