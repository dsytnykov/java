package org.example.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);

        List<String> words = Arrays.asList("apple", "banana", "cherry");
        String result = words.stream()
                .collect(Collectors.joining(", "));
        System.out.println(result);

        Map<Boolean, List<Integer>> grouped = numbers.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0));

        List<String> sortedWords = words.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        boolean allPositive = numbers.stream()
                .allMatch(n -> n > 0);

        List<String> wordsAnagrams = Arrays.asList("listen", "silent", "enlist", "rat", "tar", "god", "dog", "evil", "vile", "veil");
        Map<String, List<String>> anagrams =  wordsAnagrams.stream()
                .collect(Collectors.groupingBy(word -> {
                    char[] charArray = word.toCharArray();
                    Arrays.sort(charArray); // Sort the characters
                    return new String(charArray); // Return the sorted string
                }));
        anagrams.values().stream()
                .filter(group -> group.size() > 1) // Filter only groups with more than one anagram
                .forEach(System.out::println); // Output the anagram groups

        // Generate first 10 Fibonacci numbers
        Stream.iterate(new long[]{0, 1}, fib -> new long[]{fib[1], fib[0] + fib[1]})
                .limit(10) // Limit to the first 10 numbers
                .map(n -> n[0]) // Extract the first element of each pair
                .forEach(System.out::println);

        List<String> sentences = Arrays.asList("Hello world", "Java 8 Streams", "flatMap example");

        List<String> w = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        int target = 7;
        List<List<Integer>> pairs = nums.stream()
                .flatMap(num1 -> numbers.stream()
                        .filter(num2 -> num1 + num2 == target)
                        .map(num2 -> Arrays.asList(num1, num2)))
                .collect(Collectors.toList());


    }
}
