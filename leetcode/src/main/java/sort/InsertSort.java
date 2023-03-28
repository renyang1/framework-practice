package sort;

/**
 * @author ryang
 * @Description
 * @date 2023年02月06日 4:53 下午
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 5, 2, 3};
        sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int value = arr[i];
            int index = i - 1;
            while (index >=0 && arr[index] > value) {
                // 移动位置
                arr[index+1] = arr[index];
                index--;
            }
            arr[index+1] = value;
        }
    }
}
