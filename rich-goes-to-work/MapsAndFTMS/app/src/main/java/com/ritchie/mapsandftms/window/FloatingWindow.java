package com.ritchie.mapsandftms.window;

import static android.content.Context.WINDOW_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.ritchie.mapsandftms.R;
import com.ritchie.mapsandftms.features.MaliaoFeature;
import com.ritchie.mapsandftms.features.SkillData;
import com.ritchie.mapsandftms.proFile.Bike;
import com.ritchie.mapsandftms.proFile.BikeData1;
import com.ritchie.mapsandftms.service.RuningService;
import com.ritchie.mapsandftms.ui.VerticalProgress;
import com.ritchie.mapsandftms.util.MapsTools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class FloatingWindow implements View.OnClickListener,View.OnFocusChangeListener {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private Button button1, button2, button3, button4, button5;
    private ListView listView1;
    private DisplayMetrics displayMetrics;
    private Context context;
    private int width, high;
    private View view,activity,SpeedShowView;
    private int pid;
    private List<Map<String, Object>> deviceShow;
    public TextView textViewMain, textView2;
    public static Bike bikeData;
    private Handler handler;
    private boolean lock, superKey = false;
    private boolean scanMaps = false;
    private String[] onClickMapsItem;
    private SimpleAdapter simpleAdapter;
    private VerticalProgress verticalProgress;
    private String onClickStar, onClickEnd;
    private EditText editText3, editText4;
    private boolean bikeMonitor,bicycleMonitoringService = false;
    private long lenForI;
    private Random random;
    private MaliaoFeature maliaoFeature;




    public FloatingWindow(Context context) {
        this.context = context;
    }

    public static void upData(Bike c) {
        bikeData = c;
    }

    @SuppressLint("RtlHardcoded")
    public void initWindow() {
        if (Settings.canDrawOverlays(context)) {
            windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            layoutParams = new WindowManager.LayoutParams();
            displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
            high = displayMetrics.heightPixels;
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            layoutParams.format = PixelFormat.RGB_565;
            layoutParams.alpha = 0.8f;
            layoutParams.width = 330;
            layoutParams.height = 600;
            layoutParams.gravity = Gravity.LEFT;
            layoutParams.x = 10;
            layoutParams.y = 10;
            view = LayoutInflater.from(context).inflate(R.layout.floating_window_layout, null);
            windowManager.addView(view, layoutParams);
            button1 = view.findViewById(R.id.button1);
            button2 = view.findViewById(R.id.button2);
            button3 = view.findViewById(R.id.button3);
            listView1 = view.findViewById(R.id.list_view1);
            textViewMain = view.findViewById(R.id.text_view_main);
            verticalProgress = view.findViewById(R.id.vp_progress);
            button4 = view.findViewById(R.id.button4);
            editText3 = view.findViewById(R.id.edit_text3);
            editText4 = view.findViewById(R.id.edit_text4);
            button5 = view.findViewById(R.id.button5);
            textView2 = view.findViewById(R.id.test_view2);
            activity = view.findViewById(R.id.activity);
            random = new Random();
            handler = new Handler(Looper.myLooper());
            handler.postDelayed(runnable1, 2 * 1000);
        }
    }
    @SuppressLint("RtlHardcoded")
    public void speedShow(){
        WindowManager.LayoutParams layoutParamsSpeedShow = new WindowManager.LayoutParams();
        DisplayMetrics displayMetricsSpeedShow = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetricsSpeedShow);
        int SpeedShowWidth = displayMetrics.widthPixels;
        int SpeedShowHigh = displayMetrics.heightPixels;
        layoutParamsSpeedShow.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParamsSpeedShow.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.format = PixelFormat.RGB_565;
        layoutParams.alpha = 0.8f;
        layoutParams.width = 330;
        layoutParams.height = 600;
        layoutParams.gravity = Gravity.RIGHT;
        layoutParams.x = 10;
        layoutParams.y = 10;
        SpeedShowView = LayoutInflater.from(context).inflate(R.layout.board_view, null);
        windowManager.addView(SpeedShowView,null);


    }

    public void setAllViewOnClick() {
        button1.setOnClickListener(this::onClick);
        button2.setOnClickListener(this::onClick);
        button3.setOnClickListener(this::onClick);
        button4.setOnClickListener(this::onClick);
        button5.setOnClickListener(this::onClick);
        editText4.setOnFocusChangeListener(this::onFocusChange);
        editText3.setOnFocusChangeListener(this::onFocusChange);
        view.setOnClickListener(this::onClick);
        view.setOnTouchListener(new View.OnTouchListener() {
            final WindowManager.LayoutParams floatWindowLayoutUpdateParam = layoutParams;
            double x;
            double y;
            double px;
            double py;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = floatWindowLayoutUpdateParam.x;
                        y = floatWindowLayoutUpdateParam.y;
                        px = event.getRawX();
                        py = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        floatWindowLayoutUpdateParam.x = (int) ((x + event.getRawX()) - px);
                        floatWindowLayoutUpdateParam.y = (int) ((y + event.getRawY()) - py);
                        windowManager.updateViewLayout(view, floatWindowLayoutUpdateParam);
                        break;
                }
                return false;
            }
        });
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        updateViewLayoutForKey(hasFocus);
    }

    /**
     * ????????????????????????????????????
     */
    public void updateViewLayoutForKey(boolean trueOrFalse) {
        if (trueOrFalse == superKey) return;
        if (trueOrFalse) {
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            windowManager.updateViewLayout(view, layoutParams);
        } else {
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            windowManager.updateViewLayout(view, layoutParams);
        }
        superKey = trueOrFalse;
    }

    /**
     * ??????????????????????????????
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                button1Event();
                break;
            case R.id.button2:
                button2Evemt();
                break;
            case R.id.button3:
                button3Event();
                break;
            case R.id.button4:
                button4Event();
            case R.id.button5:
                button5Event();
        }
        updateViewLayoutForKey(false);


    }

    /**
     * ???????????????????????? ????????????
     * ??????????????????????????????
     * ????????????????????????????????????
     */
    private void button5Event() {
        String sizeOff = editText3.getText().toString();
        String value = editText4.getText().toString();
        int intSizeOff = Integer.parseInt(sizeOff);
        int intValue = Integer.parseInt(value);
        MapsTools.onceWrite(pid, onClickStar, onClickEnd, intSizeOff, intValue);


    }

    /**
     * ????????????????????????
     * ???????????????
     * ???????????????????????????????????? ????????? ????????? ??? ?????? ????????? ??? ???????????? ????????????
     */
    private void button4Event() {
       MapsTools.printfAllMem(pid, onClickStar, onClickEnd);

    }

    /**
     * ?????????????????????
     */
    private void button3Event() {


    }

    /**
     * ???????????????mapsd?????????????????????
     * ??????????????????
     * ??????maps???
     * ??????sizeOff???
     * ??????????????????
     * ????????????????????????
     **/
     private void button2Evemt() {
         synchronized(this){
             if (! bicycleMonitoringService) {
                 listView1.setVisibility(View.GONE);
                 // ????????????????????????
                 Intent intent2 = new Intent(context, RuningService.class);
                 context.startService(intent2);
                 textViewMain.setText("??????????????????");
                 bicycleMonitoringService=true;
                 speedShow();
             }
         }
    }
    /**
     * ?????????????????????*/
    private void button1Event() {
        bikeMonitor = false;
        textViewMain.setText("????????????");
        // ??????????????????
        if (deviceShow != null) {
            deviceShow.clear();
            listView1.clearAnimation();
        }
        if (simpleAdapter != null) {

            simpleAdapter = null;
        }
        listView1.setVisibility(View.GONE);

        Handler handlerSearchMaps = new Handler();
        handlerSearchMaps.post(new Runnable() {
            @Override
            public void run() {
                List<String[]> list = null;
                textViewMain.setText("????????????maps");
                try {
                    pid = MapsTools.getPid("com.ezl.emulator.nes");
                    //pid = MapsTools.getPid("com.xcnes.cjml.xc");
                    //size,
                    //list = MapsTools.getStartAndEnd(pid,1024*512);
                    list = MapsTools.getStartAndEnd(pid);

                    List<String[]> test66s = MapsTools.getTest66(pid, list);
                    textViewMain.setText("??????????????????");
                    deviceShow = new ArrayList<>();
                    for (int i = 0; i < test66s.size(); i++) {
                        HashMap<String, Object> hashMap = new HashMap();
                        hashMap.put("star", test66s.get(i)[0]);
                        hashMap.put("end", test66s.get(i)[1]);
                        deviceShow.add(hashMap);
                    }
                    textViewMain.setText("????????????");
                    simpleAdapter = new SimpleAdapter(context, deviceShow, R.layout.list_view_item_maps, new String[]{"star", "end"}, new int[]{R.id.star_addr, R.id.end_addr});
                    listView1.setAdapter(simpleAdapter);
                    listView1.setVisibility(View.VISIBLE);

                } catch (RuntimeException e) {
                    if (list != null) {
                        textViewMain.setText("?????????????????????maps????????? " + list.size() + e.getMessage());
                    } else {
                        textViewMain.setText(e.getMessage());
                    }

                }

            }
        });
        listView1.setOnItemClickListener(new OnItemClick());
    }

    class OnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView textViewStar = view.findViewById(R.id.star_addr);
            String starAddr = textViewStar.getText().toString();
            TextView textViewEnd = view.findViewById(R.id.end_addr);
            String endAddr = textViewEnd.getText().toString();
            textViewMain.setText("?????????");
            onClickMapsItem = new String[]{starAddr, endAddr};
            /*?????? 1720 01 09
             * ?????? 1720-1 01 03
             * ????????? 1720-49 01 02
             *pidaa: 2647, start-addr:0xc9700000,end-addr:0xc9780000,offset:1720 ,value:3
               he_test666:get end addr:0xc9700000 , end:0xc9780000,offset:215271
             *
             * */
            String ret = MapsTools.onceWrite(pid, starAddr, endAddr, 1720, 3);
            //MapsTools.oneSearch(pid,starAddr,endAddr);
            if (starAddr != null) {

                onClickStar = starAddr;
            }
            if (endAddr != null) {

                onClickEnd = endAddr;
            }
            //???????????????offsize
            String[] ssr = ret.split("\n");
            String sizeString = ssr[1].substring(ssr[1].indexOf("offset:") + 7, ssr[1].length()).trim();
            lenForI = Long.parseLong(sizeString);
            long star = Long.parseLong(onClickStar, 16);
            maliaoFeature = new MaliaoFeature(star, lenForI);
            textViewMain.setText(" 0x" + onClickStar + " 0x" + onClickEnd);
        }
    }

    //????????????textView
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            if (bikeData != null && bikeData instanceof BikeData1) {
                BikeData1 bikeData1 = (BikeData1) bikeData;
                int Cadence = bikeData1.getInstantaneousCadence();
                Log.d("liujunjie", "runnable1????????????????????????: " + Cadence);
                int countKM = bikeData1.getTotalDistancePresentUint();
                if (verticalProgress.getmProgress() > (countKM % 100)) {
                    vertical(countKM, true);
                } else {
                    vertical(countKM, false);
                }
                textViewMain.setText("????????????:" + Cadence);
            }
            handler.postDelayed(runnable1, 1 * 1000);

        }
        public void vertical(int countKM, boolean iso) {
            int variable = countKM % 100; //??????????????????????????????
            if (iso) {
                for (int k = verticalProgress.getmProgress(); k <100 ; k++) {
                    verticalProgress.setProgress(k);
                }
                for (int i = 0; i < variable; i++) {
                    verticalProgress.setProgress(i);
                }
                //????????????
                goldenBody(00);
            } else {
                int star = verticalProgress.getmProgress();
                for (int j = 0; j < variable - star; j++) {
                    verticalProgress.setProgress(star + j);
                }
            }
        }
    };
    public void goldenBody(int ppp) {
           int ppp1 = random.nextInt(9);
           SkillData skillData = maliaoFeature.getSkillDataList()[ppp1];
           long addr = skillData.getOffSize();
           String addr1 = String.format("%016x",addr);
           MapsTools.fastWrite1(pid,addr1 , skillData.getValue());
           Log.d("??????", "goldenBody:?????? "+skillData.getName());
           textView2.setText("?????????" + skillData.getName() + "??????5???");
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   MapsTools.fastWrite1(pid, addr1, skillData.getDefaultValue());
                   Log.d("??????", "run: ??????"+skillData.getName());
                   textView2.setText("????????????");
               }
           }, 5 * 1000);
    }

    public void goldenBody() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int ppp1 = random.nextInt(9);
                SkillData skillData = maliaoFeature.getSkillDataList()[ppp1];
                long addr = skillData.getOffSize();
                String addr1 = String.format("%016x",addr);
                MapsTools.fastWrite1(pid,addr1 , skillData.getValue());
                Log.d("??????", "goldenBody:?????? "+skillData.getName());
                textView2.setText("?????????" + skillData.getName() + "??????5???");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MapsTools.fastWrite1(pid, addr1, skillData.getDefaultValue());
                        Log.d("??????", "run: ??????"+skillData.getName());
                        textView2.setText("????????????");
                    }
                }, 5 * 1000);
                handler.postDelayed(this,6*1000);
            }

        },10*1000);

    }

}