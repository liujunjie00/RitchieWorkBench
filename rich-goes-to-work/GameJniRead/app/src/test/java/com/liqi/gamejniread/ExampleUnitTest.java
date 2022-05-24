package com.liqi.gamejniread;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.*;

import java.math.BigInteger;
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
    public void rrf(){

    String ttt = "5ff526a6";
   /* BigInteger bigInteger = new BigInteger(ttt,16);
    int ssr = bigInteger.bitCount();
        System.out.println(ssr);*/
      /*  byte [] ss = ttt.getBytes(StandardCharsets.UTF_8);*/

        int ssr = Integer.parseInt("5ff526a6",16);

        System.out.println(ssr);

    }
    @Test
    public void ssr(){
        String ss = "123";

        int[] stt = new int[]{1,2,3,};

        System.identityHashCode(stt);
        int i = System.identityHashCode(ss);
        System.out.println(i+"");
    }


}