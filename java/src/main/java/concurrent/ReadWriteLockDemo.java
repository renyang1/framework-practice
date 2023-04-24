package concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ryang
 * @Description
 * @date 2023年04月18日 23:55
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) throws InterruptedException {
        MyCache cache = new MyCache();

        // 写缓存
        for (int i = 0; i < 5; i++) {
            final int j = i;
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cache.put(String.valueOf(j), String.valueOf(j));
            }, i + "写").start();
        }

        // 读缓存
        for (int i = 0; i < 5; i++) {
            final int j = i;
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cache.get(String.valueOf(j));
            }, i + "读").start();
        }

        Thread.sleep(2000);
        Map<String, String> map = cache.getMap();
        System.out.println("size \t" + map.size() + "\t" + map.toString());
    }

}

class MyCache {

    private volatile Map<String, String> map = new HashMap<>();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, String value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入");
            try {
                // 模拟网络拥堵
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            try {
                // 模拟网络拥堵
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + value);
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    public Map<String, String> getMap() {
        return map;
    }
}
