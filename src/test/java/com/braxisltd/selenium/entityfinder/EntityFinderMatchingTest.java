package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.context.FieldContext;
import com.braxisltd.selenium.entityfinder.functions.FieldMatchers;
import com.braxisltd.selenium.entityfinder.util.DriverFactory;
import com.google.common.base.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.braxisltd.selenium.entityfinder.functions.Resolvers.text;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class EntityFinderMatchingTest {

    private WebDriver driver;
    private EntityFinder entityFinder;
    private Person person;

    @Before
    public void setUp() throws Exception {
        driver = DriverFactory.create();
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

    @Test
    public void shouldFindCheckboxChecked() throws Exception {
        List<Person> males = entityFinder.lookFor(person)
                .where(person.male()).has(FieldMatchers.checkable(true))
                .findAll();
        assertThat(males.size(), is(2));
        assertThat(males, hasItems(
                withGivenName("Darren"),
                withGivenName("Desmond")
        ));
    }

    @Test
    public void shouldFindCheckboxUnchecked() throws Exception {
        List<Person> males = entityFinder.lookFor(person)
                .where(person.male()).has(FieldMatchers.checkable(false))
                .findAll();
        assertThat(males.size(), is(1));
        assertThat(males, hasItem(withGivenName("Gemma")));
    }

    private Matcher withGivenName(final String givenName) {
        return new TypeSafeMatcher<Person>() {
            @Override
            protected boolean matchesSafely(Person person) {
                return person.givenName().resolve(text()).equals(givenName);
            }

            public void describeTo(Description description) {
                description.appendText("First name: ").appendValue(givenName);
            }
        };
    }

    interface Person {
        FieldContext givenName();

        FieldContext familyName();

        FieldContext male();
    }

}
