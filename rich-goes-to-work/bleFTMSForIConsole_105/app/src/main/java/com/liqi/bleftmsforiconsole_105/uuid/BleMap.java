package com.liqi.bleftmsforiconsole_105.uuid;



public class BleMap{
    public static final String READ ="读";
    public static final String NOTIFY ="通知";
    public static final String WRITE ="写";
    public static final String BUXIAODE ="不知道";

    private String Name;
    String ServiceUUID;
    String charUUID;
    String properties;

    public BleMap() {
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getServiceUUID() {
        return ServiceUUID;
    }

    public void setServiceUUID(String serviceUUID) {
        ServiceUUID = serviceUUID;
    }

    public String getCharUUID() {
        return charUUID;
    }

    public void setCharUUID(String charUUID) {
        this.charUUID = charUUID;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public BleMap(String name, String serviceUUID, String charUUID, String properties) {
        Name = name;
        ServiceUUID = serviceUUID;
        this.charUUID = charUUID;
        this.properties = properties;

    }



}
