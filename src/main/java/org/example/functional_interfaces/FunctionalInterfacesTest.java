package org.example.functional_interfaces;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfacesTest {
    private static final CustomInterface<String, Integer> custom = String::valueOf;

    private static final BiFunction<String, String, String> biFunction = String::concat;
    private static final Supplier<String> supplier = () -> "String from supplier";
    public static final Consumer<String> consumer = System.out::println;
    public static final Predicate<String> predicate = s -> !s.isBlank() && s.length() > 5;

    public static void functionalInterfacesUtility() {
        System.out.println(supplier.get());
        consumer.accept("String for consumer");
        System.out.println(predicate.test(""));
        System.out.println(predicate.test("Test valid string"));
        System.out.println(custom.transform(123));
    }
}
