package concurrent;

import java.util.concurrent.*;

/**
 * @author ryang
 * @Description
 * @date 2023年04月12日 11:23 上午
 */
public class ThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Integer.MAX_VALUE);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 2, 1000
                , TimeUnit.MILLISECONDS, new SynchronousQueue<>()
                , Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            final int j = i;
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t i=" + j + "\t come in");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }
}
