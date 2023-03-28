package concurrent;

import com.google.errorprone.annotations.Var;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author ryang
 * @Description
 * @date 2023年02月21日 5:37 下午
 */
public class LockTest {

    public static void main(String[] args) throws InterruptedException {
//        f1();
        System.out.println(7/10);
        System.out.println(7%10);

        System.out.println(31986990 + 36916148 + 41992720 + 43550289);
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(10);
        System.out.println(System.currentTimeMillis() - currentTimeMillis);

    }

    public static void f1() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println(1/0);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> rs = new ArrayList<>();
        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                int target = 0 - (nums[i] + nums[j]);
                int index = search(nums, target);
                if(index != i && index != j && index > 0) {
                    if(!rs.contains(Arrays.asList(nums[i], nums[j], target)))
                        rs.add(Arrays.asList(nums[i], nums[j], target));
                }
            }
        }
        return rs;
    }

    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] == target) {
                return mid;
            }
            if(target > nums[mid]) {
                low = mid + 1;
            }
            high = mid - 1;
        }
        return -1;
    }
}
