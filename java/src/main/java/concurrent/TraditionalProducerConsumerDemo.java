package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ryang
 * @Description
 * @date 2023年04月02日 5:38 下午
 */
public class TraditionalProducerConsumerDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        // 并发生产
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "p---" + i).start();
        }

        // 并发消费
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "c---" + i).start();
        }
    }

}

class ShareData {
    private int number;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            // 需要使用while进行判断，否则可能出现虚假唤醒
            while (number != 0) {
                // 等待，不能进行生产
                condition.await();
            }

            // 生产
            number++;

            System.out.println(Thread.currentThread().getName() + "\t " + number);
            // 生产完成，唤醒消费者
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            // 需要使用while进行判断，否则可能出现虚假唤醒
            while (number == 0) {
                // 等待，不能进行消费
                condition.await();
            }

            // 消费
            number--;

            System.out.println(Thread.currentThread().getName() + "\t " + number);
            // 消费结束，唤醒生产者
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

}
