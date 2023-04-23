package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ryang
 * @Description
 * @date 2023年04月18日 11:22 上午
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo demo = new SpinLockDemo();
        new Thread(() -> {
            demo.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放锁
            demo.unLock();
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            demo.lock();
            // 释放锁
            demo.unLock();
        }, "t2").start();

    }

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t lock start");
        while (!atomicReference.compareAndSet(null, thread)) {
            // 自旋
        }
        System.out.println(thread.getName() + "\t lock end");
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t unLock start");
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "\t unLock end");
    }
}
