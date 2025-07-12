package org.example.algorithm.sort;

public class MergeSort {

    private static void sort(int[] arr, int left, int right) {
        if(left < right) {
            int mid = left + (right - left) / 2;

            sort(arr, left, mid);
            sort(arr, mid + 1, right);

            merge(arr, left, right, mid);
        }
    }

    private static void merge(int[] arr, int left, int right, int mid) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for(int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }

        for(int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;

        while(i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

            while(i < n1) {
                arr[k] = L[i];
                i++;
                k++;
            }

            while(j < n2) {
                arr[k] = R[j];
                j++;
                k++;
            }

    }

    public static void sortExample() {
        int[] arr = {3, 2, 89, 34, 45, 20, 80, 34};

        sort(arr, 0, arr.length - 1);

        for (int j : arr) {
            System.out.print(j + " ");
        }
    }
}

/*
Complexity Analysis of Merge Sort:
Time Complexity:
Best Case: O(n log n), When the array is already sorted or nearly sorted.
Average Case: O(n log n), When the array is randomly ordered.
Worst Case: O(n log n), When the array is sorted in reverse order.
Auxiliary Space: O(n), Additional space is required for the temporary array used during merging.
Applications of Merge Sort:
Sorting large datasets
External sorting (when the dataset is too large to fit in memory)
Inversion counting
Merge Sort and its variations are used in library methods of programming languages. For example its variation TimSort is used in Python, Java Android and Swift. The main reason why it is preferred to sort non-primitive types is stability which is not there in QuickSort. For example Arrays.sort in Java uses QuickSort while Collections.sort uses MergeSort.
It is a preferred algorithm for sorting Linked lists.
It can be easily parallelized as we can independently sort subarrays and then merge.
The merge function of merge sort to efficiently solve the problems like union and intersection of two sorted arrays.
Advantages of Merge Sort:
Stability : Merge sort is a stable sorting algorithm, which means it maintains the relative order of equal elements in the input array.
Guaranteed worst-case performance: Merge sort has a worst-case time complexity of O(N logN) , which means it performs well even on large datasets.
Simple to implement: The divide-and-conquer approach is straightforward.
Naturally Parallel : We independently merge subarrays that makes it suitable for parallel processing.
Disadvantages of Merge Sort:
Space complexity: Merge sort requires additional memory to store the merged sub-arrays during the sorting process.
Not in-place: Merge sort is not an in-place sorting algorithm, which means it requires additional memory to store the sorted data. This can be a disadvantage in applications where memory usage is a concern.
Slower than QuickSort in general. QuickSort is more cache friendly because it works in-place.
 */
