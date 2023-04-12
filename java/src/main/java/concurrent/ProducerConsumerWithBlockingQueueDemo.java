package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ryang
 * @Description
 * @date 2023年04月02日 6:40 下午
 */
public class ProducerConsumerWithBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        MyReSource myReSource = new MyReSource(new ArrayBlockingQueue<>(1));

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    myReSource.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "c" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    myReSource.product();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "p" + i).start();
            // 这里不sleep的话，输出结果无法得到：生产结果、消费结果 这样交替的日志。
            // 因为一开始5个生产、5个消费线程都卡在offer()、poll()这里，10个线程执行结束打印日志那里无法保证顺序，应为都是并发打印
            Thread.sleep(100);
        }
    }
}

class MyReSource {

    private AtomicInteger atomicInteger = new AtomicInteger();

    private BlockingQueue<String> blockingQueue;

    public MyReSource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void product() throws InterruptedException {
        boolean rs = blockingQueue.offer(atomicInteger.incrementAndGet() + "", 2L, TimeUnit.SECONDS);
        if (!rs) {
            System.out.println("生产失败，当前线程：" + Thread.currentThread().getName());
        }
        System.out.println(Thread.currentThread().getName() + "\t 生产结果：" + atomicInteger.get() + "\t 队列长度：" + blockingQueue.size());
    }

    public void consumer() throws InterruptedException {
        String rs = blockingQueue.poll(2L, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + "\t 消费结果：" + rs + "\t 队列长度：" + blockingQueue.size());
    }
}
