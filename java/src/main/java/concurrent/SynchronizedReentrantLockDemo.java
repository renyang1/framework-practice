package concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ryang
 * @Description
 * @date 2023年04月17日 23:45
 */
public class SynchronizedReentrantLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.getLock();
        }, "t1").start();

        new Thread(() -> {
            phone.getLock();
        }, "t2").start();

    }
}

class Phone {

    Lock lock = new ReentrantLock();
    public void getLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t getLock");
            Thread.sleep(100);

            // 同步方法中调用另一个同步方法
            setLock();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void setLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t setLock");
        } finally {
            lock.unlock();
        }
    }

}
