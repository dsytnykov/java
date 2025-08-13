package org.example.useful.nullobject;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class NullObjectCheck {
    public static void main(String[] args) {
        String someNullObject = null;

        //Old way
        if(someNullObject == null) {
            System.out.println("someNullObject checked in old way using == null");
        }

        //1. Using Optional
        Optional.ofNullable(someNullObject).ifPresentOrElse(s -> System.out.println("somenNullObject checked in new way using Optional.ofNullable"), () -> System.out.println("someNullObject is null"));

        //2. Fail fast. One more possibility with throwing exception - good for the cases where we don't expect it as a null object
        //for complex objects we can use a few checks one by one
        Objects.requireNonNull(someNullObject);
        //Objects.requireNonNull(someObject.getField(), "someObject.getField() can't be null");

        //3. Also design API that doesn't return null
        List<String> list = getItems();

        //4. Use null objects or default values
        Optional.ofNullable(someNullObject).orElse("someNullObject is null");
    }

    public static List<String> getItems() {
        return Collections.emptyList();
    }
}
