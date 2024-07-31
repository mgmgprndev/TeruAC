package com.mogukun.teru.settings;


public class CheckSetting {

    String checkName, checkType, description = "?";
    int flagVL = 5;

    boolean isExperimental = false;


    public CheckSetting(){}

    public void setCheckName(String s){
        this.checkName = s;
    }

    public void setCheckType(String s){
        this.checkType = s;
    }

    public void setDescription(String s){
        this.description = s;
    }

    public void setFlagVL(int i){
        this.flagVL = i;
    }

    public void setExperimental(boolean b){
        this.isExperimental = b;
    }

    public String getCheckName(){
        return this.checkName;
    }

    public String getCheckType(){
        return this.checkType;
    }

    public String getDescription(){
        return this.description;
    }

    public int getFlagVL(){
        return this.flagVL;
    }

    public boolean isExperimental(){
        return this.isExperimental;
    }

}
