package org.example.algorithm.sort;

public class BubbleSort {

    private BubbleSort(){}

    private static void sort(int[] arr) {
        boolean swapped;
        for(int i = 0; i < arr.length; i++) {
            swapped = false;
            for(int j = 0; j < arr.length - i - 1; j++) {
                if(arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swapped = true;
                }
            }
            if(!swapped) {
                break;
            }
        }
    }

    public static void sortExample() {
        int[] arr = {3, 2, 89, 34, 45, 20, 80};

        sort(arr);

        for (int j : arr) {
            System.out.print(j + " ");
        }
    }
}

/*
*Complexity Analysis of Bubble Sort:
Time Complexity: O(n2)
Auxiliary Space: O(1)

* Advantages of Bubble Sort:
Bubble sort is easy to understand and implement.
It does not require any additional memory space.
It is a stable sorting algorithm, meaning that elements with the same key value maintain their relative order in the sorted output.
* Disadvantages of Bubble Sort:
Bubble sort has a time complexity of O(n2) which makes it very slow for large data sets.
Bubble sort is a comparison-based sorting algorithm, which means that it requires a comparison operator to determine the relative order of elements in the input data set. It can limit the efficiency of the algorithm in certain cases.
* Frequently Asked Questions (FAQs) on Bubble Sort:
What is the Boundary Case for Bubble sort?
Bubble sort takes minimum time (Order of n) when elements are already sorted. Hence it is best to check if the array is already sorted or not beforehand, to avoid O(n2) time complexity.


Does sorting happen in place in Bubble sort?
Yes, Bubble sort performs the swapping of adjacent pairs without the use of any major data structure. Hence Bubble sort algorithm is an in-place algorithm.


Is the Bubble sort algorithm stable?
Yes, the bubble sort algorithm is stable.
 */
