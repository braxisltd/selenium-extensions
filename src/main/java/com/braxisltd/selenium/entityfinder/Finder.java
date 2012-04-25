package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Optional;

import java.util.List;

public interface Finder<T> {
    Optional<T> findFirst();
    List<T> findAll();
    EntityFinder.FinderContext and();
}
