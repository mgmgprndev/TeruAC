package com.mogukun.teru.managers;

import java.util.ArrayList;
import java.util.Iterator;

public class Counter {

    ArrayList<Long> counts = new ArrayList<>();

    public Counter(){

    }

    public void count(){
        counts.add(System.currentTimeMillis());
    }

    public int getCount() {
        Iterator<Long> iterator = counts.iterator();
        while (iterator.hasNext()) {
            Long i = iterator.next();
            if (System.currentTimeMillis() - i >= 1000) {
                iterator.remove();
            }
        }
        return counts.size();
    }

}
