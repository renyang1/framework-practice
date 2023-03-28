package linkedlist;

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author ryang
 * @Description
 * @date 2022年12月26日 3:45 下午
 */
public class LinkedListTest {
    public static void main(String[] args) {

        new LinkedListTest().exchange(new int[]{2,16,3,5,13,1,16,1,12,18,11,8,11,11,5,1});
    }

    public int[] exchange(int[] nums) {
        if (nums == null || nums.length == 1) {
            return nums;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 0) {
                queue.push(i);
            }

            if (nums[i] % 2 != 0 && !queue.isEmpty()) {
                // 奇数且前面存在偶数
                // 偶数index
                int pre = queue.pop();
                int tmp = nums[pre];
                nums[pre] = nums[i];
                nums[i] = tmp;
            }
        }
        return nums;
    }
}

@EqualsAndHashCode
class Node {
    int val;
    Node next, random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

