package concurrent;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author ryang
 * @Description
 * @date 2023年03月30日 11:07 上午
 */
public class ABADemo {

    public static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference(1, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t start");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 100,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("value:" + atomicStampedReference.getReference() + "\t stamp:" + atomicStampedReference.getStamp());
            System.out.println(atomicStampedReference.compareAndSet(100, 1,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("value:" + atomicStampedReference.getReference() + "\t stamp:" + atomicStampedReference.getStamp());
        }, "AAA").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t" + stamp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 2023,
                    stamp, atomicStampedReference.getStamp() + 1));
            System.out.println("value:" + atomicStampedReference.getReference() + "\t stamp:" + atomicStampedReference.getStamp());
        }, "BBB").start();
    }

}
