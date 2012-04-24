package com.braxisltd.selenium.entityfinder;

import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;

public class CssHelper {
    private String className;

    private CssHelper(String className) {
        this.className = className;
    }

    public static CssHelper cssClass(String className) {
        return new CssHelper(className);
    }

    public boolean isPresentOn(WebElement element) {
        return getClassNames(element).contains(className);
    }

    private List<String> getClassNames(WebElement element) {
        List<String> classNames = newArrayList();
        String concatenatedClassNames = element.getAttribute("class");
        if (!isNullOrEmpty(concatenatedClassNames)) {
            for (String className : concatenatedClassNames.split("\\s")) {
                classNames.add(className);
            }
        }
        return classNames;
    }
}
