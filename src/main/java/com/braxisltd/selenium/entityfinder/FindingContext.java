package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.elements.Entity;
import com.braxisltd.selenium.entityfinder.functions.EntityConstraint;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

public class FindingContext {
    private final WebDriver driver;
    private List<EntityConstraint> entityCriteria = newArrayList();
    private String entityClassName;

    public FindingContext(WebDriver driver, final String entityClassName) {
        this.driver = driver;
        this.entityClassName = entityClassName;
    }

    public boolean found() {
        return findCandidates().size() > 0;
    }

    private List<Entity> findCandidates() {
        return newArrayList(filter(findAllEntities(), new Predicate<Entity>() {
            public boolean apply(Entity input) {
                return input.matchesCriteria(entityCriteria);
            }
        }));
    }

    private Iterable<Entity> findAllEntities() {
        return Iterables.transform(driver.findElements(By.className(entityClassName)), new Function<WebElement, Entity>() {
            public Entity apply(WebElement input) {
                return new Entity(input);
            }
        });
    }

    public FindingContext with(EntityConstraint entityConstraint) {
        entityCriteria.add(entityConstraint);
        return this;
    }
}
