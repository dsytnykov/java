package org.example.algorithm.sort;

public class InsertionSort {

    private InsertionSort(){}

    private static void sort(int[] arr) {
        for(int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while(j >= 0 && arr[j] > key) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }

    public static void sortExample() {
        int[] arr = {3, 2, 89, 34, 45, 20, 80, 34};

        sort(arr);

        for (int j : arr) {
            System.out.print(j + " ");
        }
    }

    /*
    Complexity Analysis of Insertion Sort :
Time Complexity of Insertion Sort
Best case: O(n) , If the list is already sorted, where n is the number of elements in the list.
Average case: O(n 2 ) , If the list is randomly ordered
Worst case: O(n 2 ) , If the list is in reverse order
Space Complexity of Insertion Sort
Auxiliary Space: O(1), Insertion sort requires O(1) additional space, making it a space-efficient sorting algorithm.
Advantages of Insertion Sort:
Simple and easy to implement.
Stable sorting algorithm.
Efficient for small lists and nearly sorted lists.
Space-efficient as it is an in-place algorithm.
Adoptive. the number of inversions is directly proportional to number of swaps. For example, no swapping happens for a sorted array and it takes O(n) time only.
Disadvantages of Insertion Sort:
Inefficient for large lists.
Not as efficient as other sorting algorithms (e.g., merge sort, quick sort) for most cases.
Applications of Insertion Sort:
Insertion sort is commonly used in situations where:

The list is small or nearly sorted.
Simplicity and stability are important.
Used as a subroutine in Bucket Sort
Can be useful when array is already almost sorted (very few inversions)
Since Insertion sort is suitable for small sized arrays, it is used in Hybrid Sorting algorithms along with other efficient algorithms like Quick Sort and Merge Sort. When the subarray size becomes small, we switch to insertion sort in these recursive algorithms. For example IntroSort and TimSort use insertions sort.
Frequently Asked Questions on Insertion Sort
Q1. What are the Boundary Cases of the Insertion Sort algorithm?

 Insertion sort takes the maximum time to sort if elements are sorted in reverse order. And it takes minimum time (Order of n) when elements are already sorted.


Q2. What is the Algorithmic Paradigm of the Insertion Sort algorithm?



 The Insertion Sort algorithm follows an incremental approach.


Q3. Is Insertion Sort an in-place sorting algorithm?

 Yes, insertion sort is an in-place sorting algorithm.


Q4. Is Insertion Sort a stable algorithm?

 Yes, insertion sort is a stable sorting algorithm.


Q5. When is the Insertion Sort algorithm used?

 Insertion sort is used when number of elements is small. It can also be useful when the input array is almost sorted, and only a few elements are misplaced in a complete big array.


     */
}
