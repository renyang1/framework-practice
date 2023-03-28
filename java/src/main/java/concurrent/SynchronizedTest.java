package concurrent;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ryang
 * @Description
 * @date 2023年01月09日 5:21 下午
 */
public class SynchronizedTest {
    private static Interner<String> interner = Interners.newWeakInterner();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            threadPool.execute(() -> {
                try {
                    new SynchronizedTest().lockById1(new String("ry001"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(5000);
        threadPool.shutdown();
    }

    public void lockById(String token) throws InterruptedException {
        synchronized (token.intern()) {
            System.out.println("start:" + Thread.currentThread().getId());
            Thread.sleep(100);
            System.out.println("end" + Thread.currentThread().getId());
        }
    }

    public void lockById1(String token) throws InterruptedException {
        String intern = interner.intern(token);
        synchronized (intern) {
            System.out.println("start:" + Thread.currentThread().getId());
            Thread.sleep(100);
            System.out.println("end" + Thread.currentThread().getId());
        }
    }
}
