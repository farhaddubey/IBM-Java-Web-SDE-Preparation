import java.util.*;

public class SelectionSort {

    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = arr[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = { 5, 3, 8, 4, 1 };
        selectionSort(arr);
        for (int x : arr) {
            System.out.println(x + " ");
        }
    }
}
