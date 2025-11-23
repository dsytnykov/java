package org.example.useful.replaceif.strategy;

public class ConcatWithSpaceStrategy implements Strategy {
    @Override
    public String process(String a, String b) {
        return a + " " + b;
    }
}
