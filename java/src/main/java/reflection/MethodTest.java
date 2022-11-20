package reflection;

import com.google.common.collect.Lists;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年09月20日 3:13 下午
 */
public class MethodTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method f1 = MethodTest.class.getMethod("f1");
        f1.invoke(new MethodTest());

        Method f2 = MethodTest.class.getMethod("f1", String.class, List.class);
        f2.invoke(new MethodTest(), "ryang", Lists.newArrayList());
    }


    public void f1() {
        System.out.println("f1()");
    }

    public void f1(String msg, List<User> users) {
        System.out.println("f2(String msg, List<User> users)");
    }



    @Data
    public static class User {
        private String name;
        private int age;
        private Integer salary;
    }


}
