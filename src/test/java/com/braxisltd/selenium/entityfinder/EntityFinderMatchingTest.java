package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EntityFinderMatchingTest {

    private WebDriver driver;
    private EntityFinder entityFinder;
    private Person person;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        driver.get(getClass().getResource("EntityFinderMatchingTest.html").toString());
        entityFinder = new EntityFinder(driver);
        person = entityFinder.createQuery(Person.class);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    @Test
    public void shouldFindFirst() throws Exception {
        Optional<Person> found = entityFinder.lookFor(person).findFirst();
        assertThat(found.isPresent(), is(true));
    }

    @Test
    public void shouldFindExistingByFieldTextContains() throws Exception {
        Optional<Person> found = entityFinder.lookFor(person)
                .where(person.familyName()).containsTest("Ballinger")
                .findFirst();
        assertThat(found.isPresent(), is(true));
    }

    @Test
    public void shouldNotFindNonExistentByFieldTextContains() throws Exception {
        Optional<Person> found = entityFinder.lookFor(person)
                .where(person.familyName()).containsTest("notPresent")
                .findFirst();
        assertThat(found.isPresent(), is(false));
    }

    @Test
    public void shouldFindMultipleEntities() throws Exception {
        List<Person> found = entityFinder.lookFor(person)
                .where(person.familyName()).containsTest("Ballinger")
                .findAll();
        assertThat(found.size(), is(2));
    }

    @Test
    public void shouldFindBasedOnMultipleCriteria() throws Exception {
        List<Person> found = entityFinder.lookFor(person)
                .where(person.familyName()).containsTest("Ballinger")
                .and().where(person.givenName()).containsTest("Darren")
                .findAll();
        assertThat(found.size(), is(1));
    }

    @Test
    public void shouldFindEntityById() throws Exception {
        List<Person> found = entityFinder.lookFor(person)
                .where(person).hasId("person2")
                .findAll();
        assertThat(found.size(), is(1));
    }

    interface Person {
        FieldContext givenName();
        FieldContext familyName();
    }

}
