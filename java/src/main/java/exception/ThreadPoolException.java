package exception;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author ryang
 * @Description
 * @date 2022年09月30日 1:47 下午
 */
public class ThreadPoolException {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 5, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("ry-threadPool-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {
        try {
            m1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        try {
            m2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m1() {
        System.out.println("m1()...");
        throw Exceptions.orderExists();
    }

    public static void m2() {
        System.out.println("m2()...");
        throw Exceptions.orderExists();
    }

}
