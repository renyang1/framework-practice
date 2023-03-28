package sort;

/**
 * @author ryang
 * @Description
 * @date 2023年02月06日 6:58 下午
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = new int[]{2, 4, 3, 1};
        sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 第i个最小元素的下标
            int minIndex = i;
            for(int j = i + 1; j < arr.length; j++) {
                if(arr[i] > arr[j]) {
                    minIndex = j;
                }
            }
            // 交换
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }
    }

}
