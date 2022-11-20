package concurrent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ryang
 * @Descriptionø
 * @date 2022年09月14日 6:05 下午
 */
public class ThreadPool {

    public static void main(String[] args) {
       List<Integer> list = Collections.singletonList(0);
       list.addAll(Arrays.asList(-1, -2));
    }
}
