package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ryang
 * @Description
 * @date 2023年04月02日 6:16 下午
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                shareResource.printA();
            }).start();

            new Thread(() -> {
                shareResource.printB();
            }).start();

            new Thread(() -> {
                shareResource.printC();
            }).start();
        }
    }

}

class ShareResource {
    // 0 打印A、1打印B、2打印C
    private int number = 0;

    private Lock lock = new ReentrantLock();
    // 打印A的钥匙
    Condition condition1 = lock.newCondition();
    // 打印B的钥匙
    Condition condition2 = lock.newCondition();
    // 打印C的钥匙
    Condition condition3 = lock.newCondition();

    public void printA() {
        lock.lock();

        try {
            while (number != 0) {
                // number 不等于0，不能打印A，需要等待
                condition1.await();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println("A");
            }
            number = 1;
            // 指定唤醒答应B的线程
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();

        try {
            while (number != 1) {
                // number 不等于0，不能打印A，需要等待
                condition2.await();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println("B");
            }
            number = 2;
            // 指定唤醒答应B的线程
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();

        try {
            while (number != 2) {
                // number 不等于0，不能打印A，需要等待
                condition3.await();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println("C");
            }

            number = 0;
            // 指定唤醒答应B的线程
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}