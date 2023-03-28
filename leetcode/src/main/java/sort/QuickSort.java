package sort;

import java.util.Arrays;

/**
 * @author ryang
 * @Description
 * @date 2023年02月02日 1:43 下午
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[] {11, 1, 10, 19, 8, 15};
//        quickSort(arr);
        String[] sArr = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            sArr[i] = String.valueOf(arr[i]);
        }
        Arrays.sort(sArr, (x,y) -> (x + y).compareTo(y + x));
        for (String s : sArr) {
            System.out.println(s);
        }
    }

    public static void quickSort(int[] arr) {
        quick(arr, 0, arr.length - 1);
    }

    public static void quick(int[] arr, int start, int end) {
        if(start >= end) return;
        int pivot = partition(arr, start, end);
        quick(arr, start, pivot - 1);
        quick(arr, pivot + 1, end);

    }


    public static int partition(int[] arr, int start, int end) {
        // 选取数组最后一个元素做基准数
        int pivot = arr[end];

        int i = start;
        for (int j = start; j < end; j++) {
            if(arr[j] < pivot) {
                // 当前数<基准数，需要交换位置
                if (i != j) {
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
                i++;
            }
        }

        // 交换基准数和i的位置
        int tmp = arr[i];
        arr[i] = arr[end];
        arr[end] = tmp;
        return i;
    }
}
