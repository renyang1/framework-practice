package optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.util.Optional;

/**
 * @author ryang
 * @Description
 * @date 2022年09月23日 7:07 下午
 */
@Data
public class OptionalTest {

    private A a;
    private String s1 = "";

    public static void main(String[] args) {
        OptionalTest optionalTest = null;
        // npe
//        System.out.println(optionalTest.getA().toString());

        // optionalTest= null, 无报错，无结果打印
        Optional.ofNullable(optionalTest)
                .map(OptionalTest :: getA)
                .filter(a1 -> "ryang".equals(a1.getField1()))
                .ifPresent(a1 -> System.out.println(a1.field1));

        // optionalTest != null, 无报错，无结果打印
        optionalTest = new OptionalTest();
        // map()方法中转换出的值为null,则会得到一个空的Optional 对象
        Optional<A> a = Optional.ofNullable(optionalTest).map(OptionalTest::getA);
        // 如果Optional对象为空，它不做任何操作
        Optional<A> a2 = a.filter(a1 -> "ryang".equals(a1.getField1()));
        a2.ifPresent(a1 -> System.out.println(a1.field1));

        // 正常输出： ryang
        optionalTest.setA(new A("ryang", 20));
        Optional.ofNullable(optionalTest)
                .map(OptionalTest :: getA)
                .filter(a1 -> "ryang".equals(a1.getField1()))
                .ifPresent(a1 -> System.out.println(a1.field1));

    }

    @Data
    @AllArgsConstructor
    @ToString
    public static class A {
        private String field1;
        private Integer field2;
    }
}
