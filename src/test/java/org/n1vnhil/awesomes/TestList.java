package org.n1vnhil.awesomes;

import org.junit.Test;

public class TestList {

    @Test
    public void test1() {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            list.add(i);
        }

        assert list.size() == 100;
        list.remove(20);
        assert list.size() == 99;
        list.remove(25);
        assert list.size() == 98;
        list.forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<Integer> list = new LinkedList<>();
        for(int i = 0; i < 100; i++) {
            list.add(i);
        }

        assert list.size() == 100;
        list.remove(20);
        assert list.size() == 99;
        list.remove(25);
        assert list.size() == 98;
        list.forEach(System.out::println);
    }

}
