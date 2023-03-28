import java.util.*;
import java.util.stream.IntStream;

/**
 * @author ryang
 * @Description
 * @date 2023年01月09日 10:30 上午
 */
public class Test {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode next1 = new ListNode(2);
        ListNode next2 = new ListNode(2);

        head.next = next1;
        next1.next = next2;

        ListNode listNode = deleteDuplicates(head);
        System.out.println(listNode);
    }

    public static ListNode deleteDuplicates(ListNode head) {
        Set<Integer> set = new HashSet<>();
        ListNode cur = head;
        while(cur != null && cur.next != null) {
            if(cur.val == cur.next.val) {
                set.add(cur.val);
            }
            cur = cur.next;
        }

        ListNode rs = new ListNode(0);
        cur = rs;
        while(head != null) {
            if(!set.contains(head.val)) {
                cur.next = head;
                cur = head;
            }
            head = head.next;
        }
        return rs.next;
    }



}

class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

