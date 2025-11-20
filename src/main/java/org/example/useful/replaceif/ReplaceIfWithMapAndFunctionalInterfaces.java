package org.example.useful.replaceif;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

//It can be also adapted for strategies
public class ReplaceIfWithMapAndFunctionalInterfaces {
    private final Predicate<String> p1 = Objects::isNull;
    private final Predicate<String> p2 = String::isEmpty;

    private final Map<Predicate<String>, BiFunction<String, String, String>> map = new LinkedHashMap<>(Map.of(
            p1, this::calculate1,
            p2, this::calculate2
    ));

    private String calculate1(String a, String b) {
        return a + b;
    }

    private String calculate2(String a, String b) {
        return b + a;
    }

    private String usingReplace(String forTest) {
        for(var entry : map.entrySet()) {
            if(entry.getKey().test(forTest)) {
                return entry.getValue().apply("a", "b");
            }
        }
        throw new RuntimeException("not found");
    }
}
