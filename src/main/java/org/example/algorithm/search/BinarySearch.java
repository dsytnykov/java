package org.example.algorithm.search;

public class BinarySearch {

    private static int iterateSearch(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while(low <= high) {
            int mid = low + (high - low)/2;

            if(nums[mid] == target) {
                return mid;
            }

            if(nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    private static int recursiveSearch(int[] nums, int target, int low, int high) {
        if(high >= low) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] > target) {
                return recursiveSearch(nums, target, low, mid - 1);
            }
            return recursiveSearch(nums, target, mid + 1, high);
        }

        return -1;
    }

    public static void recursiveSearchExample() {
        int arr[] = { 2, 3, 4, 10, 40 };
        int n = arr.length;
        int x = 10;
        int result = recursiveSearch(arr, x, 0, n - 1);
        if (result == -1)
            System.out.println(
                    "Element is not present in array");
        else
            System.out.println(
                    "Element is present at index " + result);
    }

    public static void iterativeSearchExample() {
        int[] arr = { 2, 3, 4, 10, 40 };
        int x = 10;
        int result = iterateSearch(arr, x);
        if (result == -1)
            System.out.println(
                    "Element is not present in array");
        else
            System.out.println(
                    "Element is present at index " + result);
    }
}

/*
* Complexity Analysis of Binary Search Algorithm
Time Complexity:
Best Case: O(1)
Average Case: O(log N)
Worst Case: O(log N)
Auxiliary Space: O(1), If the recursive call stack is considered then the auxiliary space will be O(logN).
* Applications of Binary Search Algorithm
Binary search can be used as a building block for more complex algorithms used in machine learning, such as algorithms for training neural networks or finding the optimal hyperparameters for a model.
It can be used for searching in computer graphics such as algorithms for ray tracing or texture mapping.
It can be used for searching a database.
* Advantages of Binary Search
Binary search is faster than linear search, especially for large arrays.
More efficient than other searching algorithms with a similar time complexity, such as interpolation search or exponential search.
Binary search is well-suited for searching large datasets that are stored in external memory, such as on a hard drive or in the cloud.
* Disadvantages of Binary Search
The array should be sorted.
Binary search requires that the data structure being searched be stored in contiguous memory locations.
Binary search requires that the elements of the array be comparable, meaning that they must be able to be ordered.
*
*
Frequently Asked Questions(FAQs) on Binary Search
1. What is Binary Search?
Binary search is an efficient algorithm for finding a target value within a sorted array. It works by repeatedly dividing the search interval in half.


2. How does Binary Search work?
Binary Search compares the target value to the middle element of the array. If they are equal, the search is successful. If the target is less than the middle element, the search continues in the lower half of the array. If the target is greater, the search continues in the upper half. This process repeats until the target is found or the search interval is empty.


3. What is the time complexity of Binary Search?
The time complexity of binary search is O(log2n), where n is the number of elements in the array. This is because the size of the search interval is halved in each step.


4. What are the prerequisites for Binary Search?
Binary search requires that the array is sorted in ascending or descending order. If the array is not sorted, we cannot use Binary Search to search an element in the array.


5. What happens if the array is not sorted for binary search?
If the array is not sorted, binary search may return incorrect results. It relies on the sorted nature of the array to make decisions about which half of the array to search.


6. Can binary search be applied to non-numeric data?
Yes, binary search can be applied to non-numeric data as long as there is a defined order for the elements. For example, it can be used to search for strings in alphabetical order.


7. What are some common disadvantages of Binary Search?
The disadvantage of Binary Search is that the input array needs to be sorted to decide which in which half the target element can lie. Therefore for unsorted arrays, we need to sort the array before applying Binary Search.


8. When should Binary Search be used?
Binary search should be used when searching for a target value in a sorted array, especially when the size of the array is large. It is particularly efficient for large datasets compared to linear search algorithms.


9. Can binary search be implemented recursively?
Yes, binary search can be implemented both iteratively and recursively. The recursive implementation often leads to more concise code but may have slightly higher overhead due to recursive stack space or function calls.


10. Is Binary Search always the best choice for searching in a sorted array?
While binary search is very efficient for searching in sorted arrays, there may be specific cases where other search algorithms are more appropriate, such as when dealing with small datasets or when the array is frequently modified.
* */
