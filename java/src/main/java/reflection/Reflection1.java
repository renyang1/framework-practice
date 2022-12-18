package reflection;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ryang
 * @Description
 * @date 2022年11月20日 5:06 下午
 */
public class Reflection1 {

    private void age(int age) {
        System.out.println("int age = " + age);
    }

    private void age(Integer age) {
        System.out.println("Integer age = " + age);
    }

    @Test
    public void test1() {
        Reflection1 reflection1 = new Reflection1();
        reflection1.age(18);
        reflection1.age(Integer.valueOf("18"));
    }

    @Test
    public void test2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method1 = getClass().getDeclaredMethod("age", Integer.TYPE);
        method1.invoke(this, 18);
        method1.invoke(this, Integer.valueOf(18));

        Method method2 = getClass().getDeclaredMethod("age", Integer.class);
        method2.invoke(this, 18);
        method2.invoke(this, Integer.valueOf("18"));
    }
}
