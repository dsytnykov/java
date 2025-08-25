package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class InterviewTasks {
    public static void main(String[] args) {

    }

    //----------------Find the first non-repeating character in a string-------------------------
    private static char findFirstNonRepeatingCharacter(String input) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for(int i = 0; i < input.length(); i++) {
            map.put(input.charAt(i), map.getOrDefault(input.charAt(i), 0) + 1);
        }
        for(Map.Entry<Character, Integer> entry : map.entrySet()) {
            if(entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return '\0';
    }

    private static char findFirstNonRepeatingCharacterStream(String input) {
        return input.chars()
                .mapToObj(s -> (char) s)
                .collect(Collectors.groupingBy(Character::charValue, LinkedHashMap::new,Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst().orElse('\0');
    }

    //---------------Group Anagrams from a List------------------
    private static List<List<String>> groupAnagrams(String[] str) {
        Map<String, List<String>> map = new HashMap<>();
        for(String s : str) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            map.computeIfAbsent(String.valueOf(chars), k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }

    private static List<List<String>> groupAnagramsStream(String[] str) {
        return Arrays.stream(str)
                .collect(Collectors.groupingBy(s -> {
                    char[] chars = s.toCharArray();
                    Arrays.sort(chars);
                    return String.valueOf(chars);
                }, Collectors.toList()))
                .values().stream().toList();
    }

    //------------------Detect Cycle in Linked List---------------------
    record ListNode(int val, ListNode next) {}
    private static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) {
                return true;
            }
        }
        return false;
    }

    //------------------------Coding: Two Sum Problem-----------------------
    private static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if(map.containsKey(diff)) {
                return new int[]{map.get(diff), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    //-----------------------Find Missing Number from 1 to n------------------
    private static int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = n * (n + 1) / 2;
        int actual = Arrays.stream(nums).sum();
        return sum - actual;
    }

    //--------------------Merge Two Sorted Arrays (in-place)-------------------
    private static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1; int j = n - 1; int k = n + m - 1;
        while(i >= 0 &&j >= 0) {
            if(nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }

    //------------------Longest Substring Without Repeating Characters------------------
    private static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int max = 0; int i = 0; int j = 0;

        while (j < s.length()) {
            if(!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                max = Math.max(max, set.size());
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return max;
    }

    //--------------------Find Kth Largest Element (Heap)----------------------
    private static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int num : nums) {
            minHeap.add(num);
            if(minHeap.size() > k) {
                return minHeap.poll();
            }
        }
        return 0;
    }

    //--------------------Longest Consecutive Sequence---------------------
    private static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;

        for(Integer num : set) {
            if(!set.contains(num - 1)) {
                int count = 1;
                int curr = num;
                while(set.contains(curr + 1)) {
                    curr++;
                    count++;
                }
                max = Math.max(max, count);
            }
        }
        return max;
    }

    //------------------Valid Parenthesis------------------
    /*Given a string that contains only three characters: {, }, and *. A * can represent either a {, a },
    or an empty string. Write a function to check if the expression is valid.*/

    //--------------------- K Most Frequent Words----------------------
    /*Given a list of words, return the k most frequent words sorted by frequency and then lexicographically.*/
    private static List<String> topKFrequent(List<String> words, int k) {
        return words.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> {
                    int freqCompare = Long.compare(b.getValue(), a.getValue());
                    if (freqCompare == 0) {
                        return a.getKey().compareTo(b.getKey());
                    }
                    return freqCompare;
                })
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        List<String> candidates = new ArrayList<>(frequencyMap.keySet());
        candidates.sort((w1, w2) -> {
            int freqCompare = frequencyMap.get(w2) - frequencyMap.get(w1);
            if (freqCompare == 0) {
                return w1.compareTo(w2);
            }
            return freqCompare;
        });

        return candidates.subList(0, Math.min(k, candidates.size()));
    }

    //---------------Rotate Matrix by 90 degrees------------------
    private static void rotate(int[][] matrix) {
        int n = matrix.length;
        //Transpose matrix
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        //Reverse each row
        for(int i = 0; i < n; i++) {
            int start = 0;
            int end = n - 1;
            while(start < end) {
                int temp = matrix[i][start];
                matrix[i][start] = matrix[i][end];
                matrix[i][end] = temp;
                start++;
                end --;
            }
        }
    }

    public static boolean isAnagram(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < s.length(); i++) {
            if(!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), 1);
            } else {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            }
        }

        for(int i = 0; i < t.length(); i++){
            Integer count = map.get(t.charAt(i));
            if(count == null || count < 1) {
                return false;
            }
            map.put(t.charAt(i), count - 1);
        }

        return true;
    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        if(k == 0 || nums.length < 2) return false;
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            Set<Integer> set = map.get(nums[i]);
            if( set != null) {
                set.add(i);
                map.put(nums[i], set);
            } else {
                Set<Integer> newSet = new HashSet<>();
                newSet.add(i);
                map.put(nums[i], newSet);
            }
        }

        for(Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            Set<Integer> indexesSet = entry.getValue();
            List<Integer> indexes = new ArrayList<>(indexesSet);
            if(indexes.size() > 1) {
                for(int i = 1; i < indexes.size(); i++) {
                    if (Math.abs(indexes.get(i) - indexes.get(i-1)) <= k) return true;
                }
            }
        }
        return false;
    }

    public static int canJump(int[] nums) {
        int goal = 0;
        int steps = 0;
        int current_end = 0;

        for(int i = 0; i < nums.length; i++) {
            if(goal < i+nums[i]) {
                goal = i + nums[i];
            }
            if(i == current_end) {
                current_end = goal;
                steps++;
            }
        }
        return steps;
    }

    public static int removeElement(int[] nums, int val) {
        int c = 0;

        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != val) {
                int temp = nums[i];
                nums[i] = nums[c];
                nums[c] = temp;
                c++;
            }
        }
        return c;
    }

    public static boolean isPalindrome(String s) {
        if(s == null) return false;

        String cleanedString = s.toLowerCase().replaceAll("[^A-Za-z0-9]", "");

        if(cleanedString.isEmpty() || cleanedString.length() == 1) return true;

        StringBuilder reversed = new StringBuilder(cleanedString);
        reversed.reverse();

        return cleanedString.contentEquals(reversed);
    }

    public static void rotate(int[] nums, int k) {
        //duplicate array
        int[] temp = new int[nums.length];
        //copy
        for (int i = 0; i < nums.length; i++) {
            temp[i] = nums[i];
        }
        //rotate
        for (int i = 0; i < nums.length; i++) {
            nums[(i + k) % nums.length] = temp[i];
        }
    }

    public static int removeDuplicatesII(int[] nums) {
        int intersections = 0;
        int j = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                intersections = 0;
                nums[j] = nums[i];
                j++;
            } else {
                intersections++;
                if (intersections <= 1) {
                    nums[j] = nums[i];
                    j++;
                }
            }
        }
        return j;
    }

}
