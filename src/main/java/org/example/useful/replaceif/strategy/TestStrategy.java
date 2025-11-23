package org.example.useful.replaceif.strategy;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

// Similar with map and functional interfaces
public class TestStrategy {
    private final static Map<Predicate<String>, Strategy> map = Map.of(
            String::isEmpty, new ConcatWithSpaceStrategy(),
            Objects::nonNull, new ConcatWithKommaStrategy()
    );
    public static void main(String[] args) {
        System.out.println(createStrategy(null).process("a", "b"));
        System.out.println(createStrategy("abc").process("a", "b"));
    }

    private static Strategy createStrategy(String forTest) {
        return map.entrySet().stream().filter(e -> e.getKey().test(forTest)).findFirst().map(Map.Entry::getValue).orElse(null);
    }

}
