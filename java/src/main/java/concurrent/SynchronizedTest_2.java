package concurrent;

/**
 * @author ryang
 * @Description
 * @date 2023年04月23日 21:51
 */
public class SynchronizedTest_2 {

    private static Object lock = new Object();

    public void m1() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + "\t come in...");
        }
    }
}
