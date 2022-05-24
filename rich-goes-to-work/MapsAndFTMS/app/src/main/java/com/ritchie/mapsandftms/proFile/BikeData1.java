package com.ritchie.mapsandftms.proFile;

public class BikeData1 extends Bike{
    //[0000fff1] Notify: "                   00 00 00 00 00 00 00 05 01 00 33 0F 2B 00 00 00 73 AA"

    /*      [0000fff1] Notify: "55 14 瞬时速度00 39 00 00 00 00 00 里程 27 01 00 類似一個倒計時的東西00 未知：0F 2B 00 00 00 可以跟随变化 ：9B AA"
            [0000fff1] Notify: "55 14 瞬时速度00 38 00 00 00 00 00 里程 28 01 00 類似一個倒計時的東西00 未知：0F 2B 00 00 00 可以跟随变化 ：9B AA"
            [0000fff1] Notify: "55 14 瞬时速度00 38 00 00 00 00 00 里程 28 01 00 類似一個倒計時的東西00 未知：0F 2B 00 00 00 可以跟随变化 ：9B AA"
            [0000fff1] Notify: "55 14 瞬时速度00 3A 00 00 00 00 00 里程 29 01 00 類似一個倒計時的東西00 未知：0F 2B 00 00 00 可以跟随变化 ：9E AA"
            [0000fff1] Notify: "55 14 瞬时速度00 3C 00 00 00 00 00 里程 2A 01 00 類似一個倒計時的東西00 未知：0F 2B 00 00 00 可以跟随变化 ：A1 AA"
            [0000fff1] Notify: "55 14 瞬时速度00 3C 00 00 00 00 00 里程 2A 01 00 類似一個倒計時的東西00 未知：0F 2B 00 00 00 可以跟随变化 ：A1 AA"
            [0000fff1] Notify: "55 14 瞬时速度00 40 00 00 00 00 00 里程 2B 01 00 類似一個倒計時的東西00 未知：0F 2B 00 00 00 可以跟随变化 ：A6 AA"*/
private int  MoreDataInstantaneousSpeed = 0; //瞬间速度 2
private int InstantaneousCadence =0;        //瞬间踏频 2
private int TotalDistancePresentUint=0;    //总里程   3
private int InstantaneousPowerPresent=0;   //瞬间功率  2
private int HeartRatePresent=0;            //心率       1
private int ElapsedTimePresent=0;           //消耗的时间   2

    public BikeData1() {
    }

    public int getMoreDataInstantaneousSpeed() {
        return MoreDataInstantaneousSpeed;
    }

    public void setMoreDataInstantaneousSpeed(int moreDataInstantaneousSpeed) {
        MoreDataInstantaneousSpeed = moreDataInstantaneousSpeed;
    }

    public int getInstantaneousCadence() {
        return InstantaneousCadence;
    }

    public void setInstantaneousCadence(int instantaneousCadence) {
        InstantaneousCadence = instantaneousCadence;
    }

    public int getTotalDistancePresentUint() {
        return TotalDistancePresentUint;
    }

    public void setTotalDistancePresentUint(int totalDistancePresentUint) {
        TotalDistancePresentUint = totalDistancePresentUint;
    }

    public int getInstantaneousPowerPresent() {
        return InstantaneousPowerPresent;
    }

    public void setInstantaneousPowerPresent(int instantaneousPowerPresent) {
        InstantaneousPowerPresent = instantaneousPowerPresent;
    }

    public int getHeartRatePresent() {
        return HeartRatePresent;
    }

    public void setHeartRatePresent(int heartRatePresent) {
        HeartRatePresent = heartRatePresent;
    }

    public int getElapsedTimePresent() {
        return ElapsedTimePresent;
    }

    public void setElapsedTimePresent(int elapsedTimePresent) {
        ElapsedTimePresent = elapsedTimePresent;
    }

    @Override
    public String toString() {
        return "自行车踏频{" +
                "瞬间速度=" + MoreDataInstantaneousSpeed +
                ", 瞬间踏频=" + InstantaneousCadence +
                ", 总里程=" + TotalDistancePresentUint +
                ", 瞬间功率=" + InstantaneousPowerPresent +
                ", 卡路里=" + HeartRatePresent +
                ", 消耗时间=" + ElapsedTimePresent +
                '}';
    }
}
