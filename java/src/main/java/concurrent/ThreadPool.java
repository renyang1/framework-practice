package concurrent;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ryang
 * @Descriptionø
 * @date 2022年09月14日 6:05 下午
 */
public class ThreadPool {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Map<Integer, Integer>> atomicReference = new AtomicReference<>();
        atomicReference.set(new HashMap<>());

        for (int i =0; i< 500; i++) {
            final int tmp = i;
            new Thread(()->{
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                    Map<Integer, Integer> integerIntegerMap = atomicReference.get();
                    Map<Integer, Integer> newMap = Maps.newHashMap(integerIntegerMap);
                    newMap.put(tmp, tmp);
                    System.out.println(atomicReference.compareAndSet(integerIntegerMap, newMap));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println(atomicReference.get().size() + "\t" + atomicReference.get().toString());

    }
}
