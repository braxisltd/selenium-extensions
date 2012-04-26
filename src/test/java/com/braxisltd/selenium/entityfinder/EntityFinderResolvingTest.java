package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.context.EntityContext;
import com.braxisltd.selenium.entityfinder.context.FieldContext;
import com.braxisltd.selenium.entityfinder.util.DriverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static com.braxisltd.selenium.entityfinder.functions.Resolvers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class EntityFinderResolvingTest {

    private WebDriver driver;
    private EntityFinder entityFinder;
    private Camera camera;

    @Before
    public void setUp() throws Exception {
        driver = DriverFactory.create();
        driver.get(getClass().getResource("EntityFinderResolvingTest.html").toString());
        entityFinder = new EntityFinder(driver);
        camera = entityFinder.createQuery(Camera.class);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    @Test
    public void shouldResolveFieldTextContent() throws Exception {
        Camera d90 = entityFinder.lookFor(camera).where(camera.model()).containsTest("D90").findFirst().get();
        assertThat(d90.make().resolve(text()), containsString("Nikon"));
    }

    @Test
    public void shouldResolveFieldAttribute() throws Exception {
        Camera d90 = entityFinder.lookFor(camera).where(camera.model()).containsTest("D90").findFirst().get();
        assertThat(d90.make().resolve(attribute("title")), is("camera1"));
    }

    @Test
    public void shouldResolveEntityId() throws Exception {
        Camera d90 = entityFinder.lookFor(camera).where(camera.model()).containsTest("D90").findFirst().get();
        assertThat(d90.camera().resolve(id()), is("d90"));
    }

    @Test
    public void shouldResolveCheckedStatus() throws Exception {
        Camera d90 = entityFinder.lookFor(camera).where(camera.model()).containsTest("D90").findFirst().get();
        assertThat(d90.owned().resolve(checkableIsChecked()), is(true));
    }

    @Test
    public void shouldResolveUncheckedStatus() throws Exception {
        Camera d90 = entityFinder.lookFor(camera).where(camera.model()).containsTest("D300").findFirst().get();
        assertThat(d90.owned().resolve(checkableIsChecked()), is(false));
    }

    interface Camera {
        EntityContext camera();
        FieldContext make();
        FieldContext model();
        FieldContext owned();
    }

}
