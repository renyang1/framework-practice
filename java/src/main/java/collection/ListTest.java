package collection;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ryang
 * @Description
 * @date 2022年10月09日 11:50 下午
 */
public class ListTest {

    @Test
    public void f1() {
        int[] arr = {1, 2, 3};
        List list = Arrays.asList(arr);
        System.out.println("list:" + list);
        System.out.println("size:" + list.size());
        System.out.println("class:" + list.get(0).getClass());
    }

    @Test
    public void f2() {
        List list1 = Arrays.asList(1, 2, 3);
        System.out.println("size:" + list1.size());

        Integer[] arr1 = {1, 2, 3};
        List list2 = Arrays.asList(arr1);
        System.out.println("size:" + list2.size());

        int[] arr2 = {1, 2, 3};
        List list3 = Arrays.stream(arr2).boxed().collect(Collectors.toList());
        System.out.println("size:" + list3.size());
    }

    @Test
    public void f3() {
        String[] arr = {"a", "b", "c"};
        List<String> list = new ArrayList(Arrays.asList(arr));
        System.out.println(list);

        arr[1] = "d";
        System.out.println(list);

        list.add("e");
        System.out.println(list);
    }

    @Test
    public void f4() {
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);
        System.out.print(list);
        list.add("e");
    }

    private static List<List<Integer>> data = new ArrayList<>();

    @Test
    public void f5() {
        for (int i = 0; i < 10000; i++) {
            List<Integer> list = IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList());
            data.add(list.subList(0, 1));
        }
    }

    @Test
    public void f6() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        System.out.println(list);

        List<Integer> subList = list.subList(1, 4);
        System.out.println(subList);

        subList.remove(1);
        System.out.println(list);

        subList.add(0);
        System.out.println(list);
        list.add(0);

        for (Integer integer : subList) {
            System.out.println(integer);
        }
    }

    @Test
    public void f7() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        // 方式一
        List<Integer> subList1 = new ArrayList<>(list.subList(1, 4));
        subList1.remove(1);
        System.out.println(list);
        list.add(0);
        subList1.forEach(System.out::println);

        // 方式二
        List<Integer> subList2 = list.stream().skip(1).limit(3).collect(Collectors.toList());
        subList2.remove(1);
        System.out.println(list);
        list.add(0);
        subList2.forEach(System.out::println);
    }

    @Test
    public void f8() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        System.out.println(list);

        Collection<Integer> unmodifiableCollection = Collections.unmodifiableCollection(list);
        System.out.println(unmodifiableCollection);

        list.add(0);
        System.out.println(unmodifiableCollection);

        unmodifiableCollection.add(0);
    }

    @Test
    public void f9() {
        List<String> singletonList = Collections.singletonList("a");
        System.out.println(singletonList);
        try {
            singletonList.add("b");
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            singletonList.get(1);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void f10() {
        List<Integer> list = Lists.newArrayList(1,2,3);
        for (Integer integer : list) {
            if (integer == 3) {
                list.remove(new Integer(3));
            }
            System.out.println(integer);
        }
    }

    @Test
    public void f11() {
        List<Integer> list = Lists.newArrayList(1,2,3);
        List<List<Integer>> partitionList1 = Lists.partition(list, 1);
        for (List<Integer> partition : partitionList1) {
            // 移除一片中的一个元素正常执行
            if (partition.contains(3)) {
                partition.remove(new Integer(3));
            }
            System.out.println(partition);
        }

        for (List<Integer> partition : partitionList1) {
            // 移除整片中的元素则会抛出异常：java.util.NoSuchElementException
            partition.removeAll(Lists.newArrayList(1,2));
            System.out.println(partition);
        }
    }
}
