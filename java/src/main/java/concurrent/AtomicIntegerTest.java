package concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ryang
 * @Description
 * @date 2023年03月22日 5:59 下午
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {
        User user1 = new User("ry1", 1);
        User user2 = new User("ry2", 2);
        User user3 = new User("ry1", 1);

        AtomicReference<User> atomicReference = new AtomicReference<>(user1);
        System.out.println(atomicReference.compareAndSet(user1, user2));
        System.out.println(atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(user1, user2));
        System.out.println(atomicReference.get().toString());

        atomicReference.set(user3);
        System.out.println(atomicReference.compareAndSet(user1, user2));
        System.out.println(atomicReference.get().toString());
    }
}

@Data
@AllArgsConstructor
class User {
    private String name;
    private int age;
}