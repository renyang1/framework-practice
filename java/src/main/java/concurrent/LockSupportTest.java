package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ryang
 * @Description
 * @date 2023年04月24日 09:02
 */
public class LockSupportTest {

    private static Object objectLock = new Object();

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        lockAwaitSignal();
    }

    private static void lockAwaitSignal () {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t come in...");
                // 线程等待
                try {
                    condition.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t 被唤醒...");
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t 通知...");
                // 休眠1s，验证是否t2线程释放锁后，t1线程才能再执行
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "\t 成功释放锁");
            }
        }, "t2").start();
    }

    private static void synchronizedWaitNotify () {
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t come in...");
                try {
                    // 等待
                    objectLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "\t 被唤醒");
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (objectLock) {
                // 唤醒
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t 通知");
            }
        }, "t2").start();
    }
}
