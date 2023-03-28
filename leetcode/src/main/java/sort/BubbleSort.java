package sort;

/**
 * @author ryang
 * @Description
 * @date 2023年02月06日 3:07 下午
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{4,2,3,1};
        sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if(arr[j] > arr[j+1]) {
                    // 交换位置
                    int tmp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }
}
