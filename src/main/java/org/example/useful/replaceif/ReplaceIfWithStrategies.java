package org.example.useful.replaceif;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ReplaceIfWithStrategies {
    private final List<Strategy> strategies = List.of(new Strategy(Objects::isNull, this::calculate1), new Strategy(String::isEmpty, this::calculate2));

    private String calculate1(String a, String b) {
        return a + b;
    }

    private String calculate2(String a, String b) {
        return b + a;
    }

    private String usingReplace(String forTest) {
        return strategies.stream()
                .filter(s -> s.predicate().test(forTest))
                .findFirst()
                .map(s -> s.executor().apply("a", "b"))
                .orElseThrow(RuntimeException::new);
    }
}

record Strategy(Predicate<String> predicate, BiFunction<String, String, String> executor) {}
