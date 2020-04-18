package com.codimiracle.web.middleware.content.inflation;

public interface Inflater<T> {
    void inflate(T inflatingPersistentObject);
}
