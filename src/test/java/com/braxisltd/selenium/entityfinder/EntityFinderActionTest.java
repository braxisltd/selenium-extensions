package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.context.FieldContext;
import com.braxisltd.selenium.entityfinder.functions.Actions;
import com.braxisltd.selenium.entityfinder.util.DriverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static com.braxisltd.selenium.entityfinder.functions.FieldMatchers.attribute;
import static com.braxisltd.selenium.entityfinder.functions.Resolvers.checkableIsChecked;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EntityFinderActionTest {

    private WebDriver driver;
    private EntityFinder entityFinder;
    private Entity entity;

    @Before
    public void setUp() throws Exception {
        driver = DriverFactory.create();
        driver.get(getClass().getResource("EntityFinderActionTest.html").toString());
        entityFinder = new EntityFinder(driver);
        entity = entityFinder.createQuery(Entity.class);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    @Test
    public void shouldCheckACheckbox() throws Exception {
        Finder<Entity> finder = entityFinder.lookFor(entity).where(entity.fieldA()).has(attribute("value", "valueA2"));
        assertThat(finder.findFirst().get().chosen().resolve(checkableIsChecked()), is(false));
        finder.findFirst().get().chosen().doAction(Actions.click());
        assertThat(finder.findFirst().get().chosen().resolve(checkableIsChecked()), is(true));
    }

    interface Entity {
        FieldContext chosen();
        FieldContext fieldA();
        FieldContext fieldB();
    }

}
