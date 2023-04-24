package basic;

import org.openjdk.jol.vm.VM;

/**
 * @author ryang
 * @Description
 * @date 2023年04月21日 00:23
 */
public class StringTest {
    public static void main(String[] args) {


        String s1 = new StringBuilder().append("ren").append("yang").toString();
        System.out.println(VM.current().addressOf(s1));

        String s = "renyang";
        System.out.println(VM.current().addressOf(s));

        String intern = s1.intern();
        System.out.println(VM.current().addressOf(intern));




    }
}
