package com.braxisltd.selenium;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.annotation.Nullable;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class SeleniumAssertions {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        driver.get(getClass().getResource("SeleniumAssertions.html").toString());
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    @Test
    public void shouldFindFourChildAndGrandchildDivs() throws Exception {
        WebElement container = driver.findElement(By.id("container"));
        List<String> ids = idsFor(container.findElements(By.xpath("descendant::*")));
        assertThat(ids.size(), is(4));
        assertThat(ids, hasItems("d1", "d1a", "d2", "d3"));
    }

    private List<String> idsFor(List<WebElement> elements) {
        return Lists.transform(elements, new Function<WebElement, String>() {
            public String apply(@Nullable WebElement input) {
                return input.getAttribute("id");
            }
        });
    }
}
