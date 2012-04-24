package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.elements.Entity;
import com.braxisltd.selenium.entityfinder.functions.EntityConstraint;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

public class EntityFinder {
    private WebDriver driver;

    public EntityFinder(WebDriver driver) {
        this.driver = driver;
    }

    public FinderContext lookFor(Class entityClass) {
        return new FinderContext(entityClass);
    }

    public class FinderContext<T> {

        private Class<T> entityClass;
        private Iterable<EntityConstraint> entityCriteria;

        private FinderContext(Class<T> entityClass) {
            this.entityClass = entityClass;
        }

        public Optional<T> findFirst() {
            return null;  //TODO: Auto-generated
        }

        private List<Entity> findCandidates() {
            return newArrayList(filter(findAllEntities(), new Predicate<Entity>() {
                public boolean apply(Entity input) {
                    return input.matchesCriteria(entityCriteria);
                }
            }));
        }

        private Iterable<Entity> findAllEntities() {
            return Iterables.transform(driver.findElements(By.className(entityClassName())), new Function<WebElement, Entity>() {
                public Entity apply(WebElement input) {
                    return new Entity(input);
                }
            });
        }

        private String entityClassName() {
            String simpleName = entityClass.getSimpleName();
            return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
        }
    }
}
