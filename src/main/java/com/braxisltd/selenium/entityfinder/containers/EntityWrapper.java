package com.braxisltd.selenium.entityfinder.containers;

import com.braxisltd.selenium.entityfinder.FieldContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class EntityWrapper<T> {
    private final Class<T> entityClass;
    private final WebElement entity;

    public EntityWrapper(Class<T> entityClass, WebElement entity) {
        this.entityClass = entityClass;
        this.entity = entity;
    }

    public T build() {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{entityClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return new FieldContext(entity, fieldClass(method));
            }
        });
    }

    private String fieldClass(Method method) {
        return method.getName();
    }
}
