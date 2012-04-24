package com.braxisltd.selenium.entityfinder;

import com.google.common.base.Optional;

public interface Finder<T> {
    Optional<T> findFirst();
}
