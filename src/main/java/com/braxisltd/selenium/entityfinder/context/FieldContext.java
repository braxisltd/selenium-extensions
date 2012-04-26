package com.braxisltd.selenium.entityfinder.context;

import com.google.common.base.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FieldContext extends ResolutionContext {

    private String fieldClass;

    public FieldContext(WebElement entity, String fieldClass) {
        this.fieldClass = fieldClass;
        fieldElement = findField(entity);
    }

    public FieldContext(String fieldClass) {
        this.fieldClass = fieldClass;
    }

    public Optional<WebElement> findField(WebElement entity) {
        List<WebElement> candidates = entity.findElements(By.className(fieldClass));
        if (candidates.size() > 0) {
            return Optional.of(candidates.get(0));
        } else {
            return Optional.absent();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldContext)) return false;

        FieldContext that = (FieldContext) o;

        return !(fieldClass != null ? !fieldClass.equals(that.fieldClass) : that.fieldClass != null);

    }

    @Override
    public int hashCode() {
        return fieldClass != null ? fieldClass.hashCode() : 0;
    }

}
