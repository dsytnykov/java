package org.example.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);

        List<String> words = Arrays.asList("apple", "banana", "cherry");
        String result = words.stream().collect(Collectors.joining(", "));
        System.out.println(result);

        Map<Boolean, List<Integer>> grouped = numbers.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0));

        List<String> sortedWords = words.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

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
                .toList();

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        int target = 7;
        List<List<Integer>> pairs = nums.stream()
                .flatMap(num1 -> numbers.stream()
                        .filter(num2 -> num1 + num2 == target)
                        .map(num2 -> Arrays.asList(num1, num2)))
                .toList();


        /*Merge two lists and remove duplicates */
        List<String> list1 = Arrays.asList("apple", "banana", "orange");
        List<String> list2 = Arrays.asList("banana", "grape", "apple");
        List<String> mergedList = Stream.concat(list1.stream(), list2.stream()).distinct().toList();

        /*Find the first non-repeating character in a string*/
        String input = "some text for analysis";

        input.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1L)
                .findFirst()
                .ifPresentOrElse(
                        e -> System.out.println("First non-repeating character: " + e.getKey()),
                        () -> System.out.println("No non-repeating characters found."));

        //From a list of sentences, find the top 3 longest ones.
        sentences.stream()
                .max(Comparator.comparingInt(String::length))
                .ifPresentOrElse(System.out::println, () -> System.out.println("Can't find max value"));


        //Given a list of books (title, author, year), sort them by author, then by year, then by title
        record Book(String title, String author, int year){}

        List<Book> books = List.of(
                new Book("Java concurrency in practice", "Goetz", 2006),
                new Book("Java", "unknown", 2006),
                new Book("Head First Design Patters", "Freeman", 2020),
                new Book("System Design Interview", "Xu", 2022),
                new Book("Effective Java", "Bloch", 2017)
        );
        List<Book> sortedBooks = books.stream()
                .sorted(Comparator.comparing(Book::author).thenComparing(Book::year).thenComparing(Book::title))
                .toList();

        //Partition a list of customers into premium vs. regular, and within each group, further partition into active vs. inactive.
        record Customer(String name, String level, boolean isActive) {}
        List<Customer> customers = List.of(
                new Customer("John Doe", "premium", true),
                new Customer("Jane Doe", "regular", true),
                new Customer("Bob Smith", "premium", false),
                new Customer("Alice Johnson", "regular", true),
                new Customer("Michael Brown", "premium", true),
                new Customer("Emily Davis", "regular", false),
                new Customer("David Wilson", "premium", true),
                new Customer("Sarah Anderson", "regular", false)
        );

        Map<String, Map<Boolean, List<Customer>>> collectCustomers = customers.stream()
                .collect(Collectors.groupingBy(
                        Customer::level,
                        Collectors.partitioningBy(Customer::isActive)));

        System.out.println(collectCustomers);

    }
}
