package com.braxisltd.selenium.entityfinder;

import com.braxisltd.selenium.entityfinder.containers.Entity;
import com.braxisltd.selenium.entityfinder.functions.EntityConstraint;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

public class EntityFinder {
    private WebDriver driver;

    public EntityFinder(WebDriver driver) {
        this.driver = driver;
    }

    public <T> FinderContext<T> lookFor(T query) {
        return new FinderContext(query.getClass().getInterfaces()[0]);
    }

    public <T> T createQuery(Class<T> entityClass) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{entityClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return new FieldContext(method.getName());
            }
        });
    }

    public class FinderContext<T> implements Finder<T> {

        private Class<T> entityClass;
        private List<EntityConstraint> entityCriteria = newArrayList();
        private Multimap<FieldContext, Predicate<WebElement>> fieldConditions = LinkedListMultimap.create();

        private FinderContext(Class<T> entityClass) {
            this.entityClass = entityClass;
        }

        public Optional<T> findFirst() {
            List<Entity> candidates = findCandidates();
            if (candidates.size() > 0) {
                return Optional.of(candidates.get(0).make(entityClass));
            } else {
                return Optional.absent();
            }
        }

        //TODO refactor this god-awful mess!
        private List<Entity> findCandidates() {
            final List<EntityConstraint> constraints = newArrayList(entityCriteria);
            constraints.add(new EntityConstraint() {
                public boolean apply(@Nullable WebElement input) {
                    for (FieldContext fieldContext : fieldConditions.keys()) {
                        Optional<WebElement> field = fieldContext.findField(input);
                        if (!field.isPresent()) {
                            return false;
                        }
                        for (Predicate<WebElement> fieldPredicate : fieldConditions.get(fieldContext)) {
                            if (!fieldPredicate.apply(field.get())) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            });
            return newArrayList(filter(findAllEntities(), new Predicate<Entity>() {
                public boolean apply(Entity input) {
                    return input.matchesCriteria(constraints);
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

        public MatcherProvider where(FieldContext fieldContext) {
            MatcherProvider matcherProvider = new MatcherProvider(this);
            fieldConditions.put(fieldContext,matcherProvider);
            return matcherProvider;
        }
    }
}
