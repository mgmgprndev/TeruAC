package com.mogukun.teru.managers;
public class ViolationData {

    public String check, type;
    public long timeStamp;

    public ViolationData(String check, String type){
        this.check = check;
        this.type = type;
        this.timeStamp = System.currentTimeMillis();
    }

}
