package com.liqi.bleftmsforiconsole_105;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.liqi.bleftmsforiconsole_105.proFile.BikeData;

import java.nio.charset.StandardCharsets;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void sss(){
        String srr = "f";
        stringHexTo10jinzi(srr);




    }
    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    public static String stringHexTo10jinzi(String hexString){
        String[] jinzi16={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        byte[] hexChar = hexString.getBytes(StandardCharsets.UTF_8);
        int count10 = 0;
        for (int i = 0; i <hexChar.length ; i++) {
            for (int j = 0; j < jinzi16.length; j++) {

            }
        }


        return null;
    }
    @Test
    public  void test(){
        BikeData bikeData = new BikeData();
        //fe瞬间速度：06 27 平均速度：00 00 瞬时踏频：82 03 平均踏频 00 00 里程：86 06 00 训练等级：01 00 瞬间功率：21 03 平均功率： 00 00 总消耗能量：0D 00 每小时消耗的能量：00 00 每分钟消耗的能量：00 00 00 消耗的时间：52 00 剩余的时间：00 00"
        //先将数据处理 不处理前面两位数据
        //瞬间速度
        byte[] bytes = {1,2,2};
        int InstantaneousSpeed_Uint161 = bytes[2]&0xff+0x100;
        int InstantaneousSpeed_Uint162 = bytes[3]&0xff+0x100;
        Log.d("liujunjie1",""+InstantaneousSpeed_Uint162*100+"hhhhhh"+InstantaneousSpeed_Uint161);
        bikeData.setInstantaneousSpeed_Uint16(InstantaneousSpeed_Uint162*100+InstantaneousSpeed_Uint161);
        //平均速度
        int AverageSpeedPresent_Uint16 = bytes[4]&0xff+0x100;
        int AverageSpeedPresent_Uint161 = bytes[5]&0xff+0x100;
        bikeData.setAverageSpeedPresent_Uint16(AverageSpeedPresent_Uint161*100+AverageSpeedPresent_Uint16);
        //瞬时踏频
        int InstantaneousCadencePresent_Uint16 = bytes[6]&0xff+0x100;
        int InstantaneousCadencePresent_Uint161 = bytes[7]&0xff+0x100;
        bikeData.setInstantaneousCadencePresent_Uint16(InstantaneousCadencePresent_Uint161*100+InstantaneousCadencePresent_Uint16);
        //平均踏频
        int AverageCadencePresent_Uint16 = bytes[8]&0xff+0x100;
        int AverageCadencePresent_Uint161 = bytes[9]&0xff+0x100;
        bikeData.setAverageCadencePresent_Uint16(AverageCadencePresent_Uint161*100+AverageCadencePresent_Uint16);
        //里程
        int TotalDistancePresent_ = bytes[10]&0xff+0x100;
        int TotalDistancePresent_1 = bytes[11]&0xff+0x100;
        int TotalDistancePresent_2 = bytes[12]&0xff+0x100;
        Log.d("liujunjie2","个位"+TotalDistancePresent_+"百位"+TotalDistancePresent_1+"千位"+TotalDistancePresent_2);
        bikeData.setTotalDistancePresent_((TotalDistancePresent_2*10000)+(TotalDistancePresent_1*100)+TotalDistancePresent_);
        //训练等级
        int ResistanceLevelPresent = bytes[13]&0xff+0x100;
        int ResistanceLevelPresent1 = bytes[14]&0xff+0x100;
        bikeData.setResistanceLevelPresent(ResistanceLevelPresent1*100+ResistanceLevelPresent);
        //瞬间功率
        int InstantaneousPower = bytes[15]&0xff+0x100;
        int InstantaneousPower1 = bytes[16]&0xff+0x100;
        bikeData.setInstantaneousPower(InstantaneousPower1*100+InstantaneousPower);
        //平均功率
        int AveragePowerPresent = bytes[17]&0xff+0x100;
        int AveragePowerPresent1 = bytes[18]&0xff+0x100;
        bikeData.setAveragePowerPresent(AveragePowerPresent1*100+AveragePowerPresent);
        //总消耗能量
        int TotalEnergyField = bytes[19]&0xff+0x100;
        int TotalEnergyField1 = bytes[20]&0xff+0x100;
        bikeData.setTotalEnergyField(TotalEnergyField1*100+TotalEnergyField);
        //每小时消耗的能量
        int EnergyPerHour = bytes[21]&0xff+0x100;
        int EnergyPerHour1 = bytes[22]&0xff+0x100;
        bikeData.setEnergyPerHour(EnergyPerHour1*100+EnergyPerHour);
        //每分钟消耗的能量
        int EnergyPerMinute = bytes[23]&0xff+0x100;
        bikeData.setEnergyPerMinute(EnergyPerMinute);
        //心率
        int HeartRatePresent = bytes[24]&0xff+0x100;
        bikeData.setHeartRatePresent(HeartRatePresent);
        //耗氧量
        int MetabolicEquivalentPresent = bytes[25]&0xff+0x100;
        bikeData.setMetabolicEquivalentPresent(MetabolicEquivalentPresent);
        //消耗的时间
        int ElapsedTimePresent = bytes[26]&0xff+0x100;
        int ElapsedTimePresent1 = bytes[27]&0xff+0x100;
        bikeData.setElapsedTimePresent(ElapsedTimePresent1*100+ElapsedTimePresent);
        //剩余时间
        int RemainingTimePresent = bytes[28]&0xff+0x100;
        int RemainingTimePresent1 = bytes[29]&0xff+0x100;
        bikeData.setRemainingTimePresent(RemainingTimePresent1*100+RemainingTimePresent);




    }
    @Test
    public void shdw(){
        String ff = "ff";
        byte[] sss = ff.getBytes(StandardCharsets.UTF_8);
        int dfjwbnfjc = byteArrayToInt(sss);
        System.out.println(dfjwbnfjc+"");


    }

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
        int shift = (4 - 1 - i) * 8;
        value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

    public static BikeData getBikeData(byte[] bytes){
        BikeData bikeData = new BikeData();
        //fe瞬间速度：06 27 平均速度：00 00 瞬时踏频：82 03 平均踏频 00 00 里程：86 06 00 训练等级：01 00 瞬间功率：21 03 平均功率： 00 00 总消耗能量：0D 00 每小时消耗的能量：00 00 每分钟消耗的能量：00 00 00 消耗的时间：52 00 剩余的时间：00 00"
        //先将数据处理 不处理前面两位数据

        String ppp = byte2Hex(bytes);
        //瞬间速度
        int ss =(bytes[2]&0xff)|((bytes[3]&0xff)<<8);
        bikeData.setInstantaneousSpeed_Uint16(ss);
        //平均速度
        int ss1 =(bytes[4]&0xff)|((bytes[5]&0xff)<<8);
        bikeData.setAverageSpeedPresent_Uint16(ss1);
        //瞬时踏频
        int ss2 =(bytes[6]&0xff)|((bytes[7]&0xff)<<8);
        bikeData.setInstantaneousCadencePresent_Uint16(ss2);
        //平均踏频
        int ss3 =(bytes[8]&0xff)|((bytes[9]&0xff)<<8);
        bikeData.setAverageCadencePresent_Uint16(ss3);
        //里程
        int ss4 =((bytes[10]&0xff)|((bytes[11]&0xff)<<8))|((bytes[12]&0xff)<<16);
        bikeData.setTotalDistancePresent_(ss4);
        //训练等级
        int ss5 =(bytes[13]&0xff)|((bytes[14]&0xff)<<8);
        bikeData.setResistanceLevelPresent(ss5);
        //瞬间功率
        int ss6 =(bytes[15]&0xff)|((bytes[16]&0xff)<<8);
        bikeData.setInstantaneousPower(ss6);
        //平均功率
        int ss7 =(bytes[17]&0xff)|((bytes[18]&0xff)<<8);
        bikeData.setAveragePowerPresent(ss7);
        //总消耗能量
        int ss8 =(bytes[19]&0xff)|((bytes[20]&0xff)<<8);
        bikeData.setTotalEnergyField(ss8);
        //每小时消耗的能量
        int ss9 =(bytes[21]&0xff)|((bytes[22]&0xff)<<8);
        bikeData.setEnergyPerHour(ss9);
        //每分钟消耗的能量
        int EnergyPerMinute = bytes[23]&0xff;
        bikeData.setEnergyPerMinute(EnergyPerMinute);
        //心率
        int HeartRatePresent = bytes[24]&0xff;
        bikeData.setHeartRatePresent(HeartRatePresent);
        //耗氧量
        int MetabolicEquivalentPresent = bytes[25]&0xff;
        bikeData.setMetabolicEquivalentPresent(MetabolicEquivalentPresent);
        //消耗的时间
        int SS10 =(bytes[26]&0xff)|((bytes[27]&0xff)<<8);
        bikeData.setElapsedTimePresent(SS10);
        //剩余时间
        int SS11 =(bytes[28]&0xff)|((bytes[29]&0xff)<<8);
        bikeData.setRemainingTimePresent(SS11);

        return bikeData;
    }

}