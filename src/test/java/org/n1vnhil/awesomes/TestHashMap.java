package org.n1vnhil.awesomes;


import org.junit.Test;
import org.n1vnhil.awesomes.utils.HashMap;

public class TestHashMap {

    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    @Test
    public void test() {
        int count = 100000;
        for(int i=0; i<count; i++) hashMap.put(i, i);
        assert hashMap.size() == count;
        for(int i=0; i<count; i++) {
            assert hashMap.get(i).equals(i);
        }

        hashMap.remove(8);
        assert hashMap.get(8) == null;
        assert hashMap.size() == count - 1;

    }

}
