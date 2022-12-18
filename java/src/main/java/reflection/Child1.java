package reflection;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ryang
 * @Description
 * @date 2022年11月20日 9:24 下午
 */
public class Child1 extends Patent{

    public void setValue(String value) {
        System.out.println("Child1.setValue() called");
        super.setValue(value);
    }

    @Test
    public void test1() throws InvocationTargetException, IllegalAccessException {
        Child1 child1 = new Child1();
        Method[] methods = child1.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("setValue")) {
                method.invoke(child1, "test1");
            }
        }
        System.out.println(child1.toString());
    }

    @Test
    public void test2() throws InvocationTargetException, IllegalAccessException {
        Child1 child1 = new Child1();
        Method[] methods = child1.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("setValue")) {
                method.invoke(child1, "test1");
            }
        }
        System.out.println(child1.toString());
    }
}
