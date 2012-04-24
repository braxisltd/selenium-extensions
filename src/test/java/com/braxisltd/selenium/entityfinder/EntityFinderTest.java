package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EntityFinderTest {

    private WebDriver driver;
    private EntityFinder entityFinder;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        driver.get(getClass().getResource("EntityFinderTest.html").toString());
        entityFinder = new EntityFinder(driver);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }
    
    @Test
    public void shouldFindFirst() throws Exception {
        Person person = entityFinder.createQuery(Person.class);
        Optional<Person> found = entityFinder.lookFor(person).findFirst();
        assertThat(found.isPresent(), is(true));
    }

    @Test
    public void shouldFindExistingByFieldTextContains() throws Exception {
        Person person = entityFinder.createQuery(Person.class);
        Optional<Person> found = entityFinder.lookFor(person).where(person.familyName()).containsTest("Ballinger").findFirst();
        assertThat(found.isPresent(), is(true));
    }

    @Test
    public void shouldNotFindNonExistentByFieldTextContains() throws Exception {
        Person person = entityFinder.createQuery(Person.class);
        Optional<Person> found = entityFinder.lookFor(person).where(person.familyName()).containsTest("notPresent").findFirst();
        assertThat(found.isPresent(), is(false));
    }

    interface Person {
        FieldContext givenName();
        FieldContext familyName();
    }
    
}
