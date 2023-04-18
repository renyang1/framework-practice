package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ryang
 * @Description
 * @date 2023年04月18日 08:54
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spinLockDemo.unLock();
        }, "t1").start();

        // 主线程休眠1s，让t1线程先执行
        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            spinLockDemo.lock();
            spinLockDemo.unLock();
        }, "t2").start();
    }

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in");
        while (!atomicReference.compareAndSet(null, thread)) {
            // 自旋
        }
        System.out.println(thread.getName() + "\t lock end");
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in");
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "\t unLock end");
    }
}
