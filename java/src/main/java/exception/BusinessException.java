package exception;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ryang
 * @Description
 * @date 2022年10月08日 2:16 下午
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static void main(String[] args) {
        AtomicReference<Integer> atomicReference = new AtomicReference<>();
        atomicReference.set(100);
        System.out.println(atomicReference.compareAndSet(100, 200));
        System.out.println(atomicReference.compareAndSet(200, 100));


        Integer a = 100;
        Integer b = 100;

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());

        Integer c = 200;
        Integer d = 200;

        System.out.println(c.hashCode());
        System.out.println(d.hashCode());
    }
}
