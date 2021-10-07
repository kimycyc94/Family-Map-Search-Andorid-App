package com.example.familymapclient;

public class Setting {
    private static Setting instance = null;
    public boolean lifeLines = true;
    public boolean familyTreeLines = true;
    public boolean spouseLines = true;
    public boolean fatherSide = true;
    public boolean motherSide = true;
    public boolean maleEvents = true;
    public boolean femaleEvents = true;

    public static Setting getInstance() {
        if (instance == null) {
            instance = new Setting();
        }
        return instance;
    }
}