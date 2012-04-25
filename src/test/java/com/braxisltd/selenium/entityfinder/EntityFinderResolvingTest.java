package com.braxisltd.selenium.entityfinder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static com.braxisltd.selenium.entityfinder.Resolutions.attribute;
import static com.braxisltd.selenium.entityfinder.Resolutions.id;
import static com.braxisltd.selenium.entityfinder.Resolutions.text;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class EntityFinderResolvingTest {

    private WebDriver driver;
    private EntityFinder entityFinder;
    private Camera camera;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
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

    interface Camera {
        EntityContext camera();
        FieldContext make();
        FieldContext model();
    }

}
