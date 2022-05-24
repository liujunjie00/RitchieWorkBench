package com.ritchie.mapsandftms.features;

public class SkillData {
    private int index;
    private long offSize;
    private int Value;
    private int defaultValue;
    private String name;

    public SkillData(int index,long offSize, int value, int defaultValue,String name) {
        this.offSize = offSize;
        Value = value;
        this.defaultValue = defaultValue;
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public long getOffSize() {
        return offSize;
    }

    public int getValue() {
        return Value;
    }

    public int getDefaultValue() {
        return defaultValue;
    }
    public String getName() {
        return name;
    }
}
