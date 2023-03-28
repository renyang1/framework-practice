package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ryang
 * @Description
 * @date 2023年03月17日 2:00 下午
 */
public class VolatileTest {
     int a = 0;
     boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100000; i++) {
            m3();
        }
    }

    public void m1() {
        a = 1;
        flag = true;
    }

    public void m2() {
        if (flag) {
            a += 5;
        }
        if (a == 5) {
            System.out.println(a);
        }
    }

    public static void m3() throws InterruptedException {
        VolatileTest volatileTest = new VolatileTest();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            volatileTest.m1();
            countDownLatch.countDown();
        }, String.valueOf("A1")).start();

        new Thread(() -> {
            volatileTest.m2();
            countDownLatch.countDown();
        }, String.valueOf("A2")).start();

        countDownLatch.await();
    }
}

class Data {
    public AtomicInteger number = new AtomicInteger(0);

    public synchronized void add(int i) {
        number.addAndGet(i);
    }
}