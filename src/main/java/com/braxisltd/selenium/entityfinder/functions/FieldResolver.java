package com.braxisltd.selenium.entityfinder.functions;

import com.google.common.base.Optional;
import org.openqa.selenium.WebElement;

public interface FieldResolver<T> {
    T resolver(WebElement fieldElement);
}
