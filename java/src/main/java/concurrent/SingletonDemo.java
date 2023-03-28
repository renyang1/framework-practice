package concurrent;

/**
 * @author ryang
 * @Description
 * @date 2023年03月19日 9:48 下午
 */
public class SingletonDemo {

    private static SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName());
    }

    private static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                SingletonDemo instance = getInstance();
                if (instance == null) {
                    System.out.println(instance);
                }
            }, String.valueOf(i)).start();
        }
        Thread.sleep(2000);
    }
}
