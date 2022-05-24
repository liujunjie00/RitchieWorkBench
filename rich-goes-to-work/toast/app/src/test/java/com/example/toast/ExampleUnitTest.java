package com.example.toast;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
    public void dddd() {
        String packageliststr = "Running activities (most recent first):\n" +
                "        Run #0: ActivityRecord{c90e9d4 u0 com.android.launcher3/.Launcher t19}\n" +
                "    Running activities (most recent first):\n" +
                "        Run #0: ActivityRecord{a19858d u0 com.android.documentsui/.files.FilesActivity t30}\n" +
                "    Running activities (most recent first):\n" +
                "        Run #1: ActivityRecord{f2fa6e7 u0 com.android.email/.activity.setup.AccountSetupFinal t32}\n" +
                "        Run #0: ActivityRecord{d54552b u0 com.android.email/.activity.Welcome t32}\n" +
                "    Running activities (most recent first):\n" +
                "        Run #0: ActivityRecord{a7a3a80 u0 com.android.rk/.RockExplorer t31}";
        //dumpsys activity activities | grep -i run
        //String packageListStr = SystemUtil.execShellCmd("dumpsys activity activities | grep -i run");

        String[] packageList = packageliststr.trim().split("\n");
        for (int i = 0; i < packageList.length; i++) {
            if (packageList[i].contains("{") && packageList[i].contains("}")) {
                String substr1 = packageList[i].substring(packageList[i].indexOf("{"), packageList[i].indexOf("/"));
                String substr2 = substr1.substring(substr1.indexOf(" "),substr1.length());
                substr1 = substr2.substring(substr2.indexOf("com"), substr2.length());
                //SystemUtil.execShellCmd("pm clear "+substr1);
                //android.util.Log.d("liujunjie", "执行命令" + i + "pm clear " + substr1);
                System.out.println(substr1);


            }
        }
    }

}