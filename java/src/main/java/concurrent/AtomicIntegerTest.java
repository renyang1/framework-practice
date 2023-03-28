package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ryang
 * @Description
 * @date 2023年03月22日 5:59 下午
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

        System.out.println(atomicInteger.compareAndSet(0, 1));
        System.out.println(atomicInteger.compareAndSet(0, 10));
    }
}
