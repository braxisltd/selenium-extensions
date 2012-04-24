package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.functions.EntityConstraint;
import com.braxisltd.selenium.entityfinder.functions.EntityFieldConstraint;
import com.braxisltd.selenium.entityfinder.functions.FieldConstraint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.annotation.Nullable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EntityFinderMatchingTest {

    private HtmlUnitDriver driver;
    private EntityFinder entityFinder;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        driver.get(getClass().getResource("EntityFinderMatchingTest.html").toString());
        entityFinder = new EntityFinder(driver);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    @Test
    public void shouldFindByClass() throws Exception {
        assertThat(entityFinder.find("person").found(), is(true));
    }

    @Test
    public void shouldNotFindByClassNotPresent() throws Exception {
        assertThat(entityFinder.find("notPresent").found(), is(false));
    }

    @Test
    public void shouldFindEntityUsingField() throws Exception {
        assertThat(entityFinder
                .find("person")
                .with(new EntityConstraint() {
                    public boolean apply(WebElement input) {
                        WebElement familyNameElement = input.findElement(By.className("familyName"));
                        return familyNameElement != null && familyNameElement.getText().equals("Ballinger");
                    }
                })
                .found(),
                is(true));
    }

    @Test
    public void shouldNotFindEntityUsingFieldNotPresent() throws Exception {
        assertThat(entityFinder
                .find("person")
                .with(new EntityConstraint() {
                    public boolean apply(WebElement input) {
                        WebElement familyNameElement = input.findElement(By.className("familyName"));
                        return familyNameElement != null && familyNameElement.getText().equals("notPresent");
                    }
                })
                .found(),
                is(false));
    }

    @Test
    public void shouldFindEntityUsingEntityFieldConstraint() throws Exception {
        assertThat(entityFinder
                .find("person")
                .with(new EntityFieldConstraint(new FieldConstraint() {
                    public boolean apply(@Nullable WebElement input) {
                        return input.getText().equals("Ballinger");
                    }
                }))
                .found(),
                is(true));
    }

    @Test
    public void shouldNotFindEntityUsingEntityFieldConstraintNotPresent() throws Exception {
        assertThat(entityFinder
                .find("person")
                .with(new EntityFieldConstraint(new FieldConstraint() {
                    public boolean apply(@Nullable WebElement input) {
                        return input.getText().equals("notPresent");
                    }
                }))
                .found(),
                is(false));
    }
}
