package concurrent;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @author ryang
 * @Description
 * @date 2023年04月05日 4:25 下午
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
//        countDownLatch();
//        cyclicBarrier();
        semaphore();
        System.out.println(Thread.currentThread().getName() + "\t 结束");
    }

    private static void semaphore() {
        Semaphore semaphore = new Semaphore(3, false);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t start");
                    Thread.sleep(500);
                    // 不用cyclicBarrier.await();每个线程end时间不在同一秒，用了则在同一秒结束
                    System.out.println(Thread.currentThread().getName() + "\t end" + "\t time:" + new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, "thread-" + i).start();
        }
    }

    private static void cyclicBarrier() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3 + 1);

        for (int i = 0; i < 3; i++) {
            final int j = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t start");
                try {
                    Thread.sleep(j * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    cyclicBarrier.await();
                    // 不用cyclicBarrier.await();每个线程end时间不在同一秒，用了则在同一秒结束
                    System.out.println(Thread.currentThread().getName() + "\t end" + "\t time:" + new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread-" + i).start();
        }
        // 将主线程也加入cyclicBarrier.await()中，可以达到和CountDownLatch 一样的使用效果
        cyclicBarrier.await();
    }

    private static void countDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }, "thread-" + i).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 主线程结束");
    }
}
