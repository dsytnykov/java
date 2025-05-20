package org.example.functional_interfaces;

@FunctionalInterface
public interface CustomInterface<T, R> {
    T transform(R r);
}
