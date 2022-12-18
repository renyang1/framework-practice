package reflection;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ryang
 * @Description
 * @date 2022年11月20日 9:20 下午
 */
public class Patent<T> {
    // 记录value 的更新次数
    AtomicInteger updateCount = new AtomicInteger();

    private T value;

    @Override
    public String toString() {
        return String.format("value: %s updateCount: %d", value, updateCount.get());
    }

    public void setValue(T value) {
        System.out.println("parent.setValue() called");
        this.value = value;
        updateCount.incrementAndGet();
    }
}
