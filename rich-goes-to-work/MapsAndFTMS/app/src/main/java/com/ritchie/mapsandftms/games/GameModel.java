package com.ritchie.mapsandftms.games;

import static android.content.Context.WINDOW_SERVICE;

import static com.ritchie.mapsandftms.window.FloatingWindow.bikeData;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
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
import com.ritchie.mapsandftms.features.BaseCharacter;
import com.ritchie.mapsandftms.features.MaliaoFeature;
import com.ritchie.mapsandftms.features.SkillData;
import com.ritchie.mapsandftms.features.TankeFeature;
import com.ritchie.mapsandftms.proFile.Bike;
import com.ritchie.mapsandftms.proFile.BikeData1;
import com.ritchie.mapsandftms.service.RuningService;
import com.ritchie.mapsandftms.ui.VerticalProgress;
import com.ritchie.mapsandftms.util.MapsTools;
import com.ritchie.mapsandftms.window.FloatingWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 这个一个游戏配置类
 * */
public class GameModel implements View.OnClickListener{
    private List<Map<String, Object>> deviceShow;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private  DisplayMetrics displayMetrics;
    private View view;
    private Button button1,button2,button3,button4,button5;
    private ListView listView1;
    private TextView textViewMain,textView2;
    private VerticalProgress verticalProgress;
    private EditText editText3,editText4;
    private Random random;
    private Handler handler;
    private String gameName;
    private BaseCharacter baseCharacter;            // 这个表示坦克大战的字符串
    private SkillData[] skillDataList;         // 这个是技能数组
    private Context context;
    private int pid;
    private SimpleAdapter simpleAdapter;
    private String[] onClickMapsItem;
    private String onClickStar;
    private String onClickEnd;
    private boolean superKey = false;
    private long lenForI;
    private MaliaoFeature maliaoFeature;
    private List<String[]> test66s;
    private long head;
    private TankeFeature tankeFeature;
    private boolean starGame = false;
    private int test;

    public void initWindow(){
        if (Settings.canDrawOverlays(context)) {
            windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            layoutParams = new WindowManager.LayoutParams();
            displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
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
            random = new Random();
            handler = new Handler(Looper.myLooper());
            //他创建出来的那一刻就是监听
            handler.postDelayed(runnable1, 2 * 1000);
        }
    }
    /**
     * 注册所有的点击事件*/
    public void setAllViewOnClick() {
        button1.setOnClickListener(this::onClick);
        button2.setOnClickListener(this::onClick);
        button3.setOnClickListener(this::onClick);
        button4.setOnClickListener(this::onClick);
        button5.setOnClickListener(this::onClick);
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
    /**
     * 实现所有的点击事件
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
     * 这个是一个键盘切换处理器
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




    private void button5Event() {
    }

    private void button4Event() {
    }

    private void button3Event() {
    }
    /**
     * 开始游戏按钮
     * */
    private void button2Evemt() {
        if (!starGame){
            tankeFeature.init(head);
            Intent intent2 = new Intent(context, RuningService.class);
            context.startService(intent2);
            textViewMain.setText("正在寻找单车");
            starGame=true;

        }
    }
    //开始扫描maps ，这里需要对新建的参数进行判断
    private void button1Event() {
        textViewMain.setText("正在查找");
        // 开辟一条线程
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
                textViewMain.setText("正在读取maps");
                try {
                    pid = MapsTools.getPid("com.ezl.emulator.nes");
                    list = MapsTools.getStartAndEnd(pid);

                    test66s = MapsTools.getTest66(pid, list,baseCharacter);
                    textViewMain.setText("正在扫描内存");
                    deviceShow = new ArrayList<>();
                    for (int i = 0; i < test66s.size(); i++) {
                        HashMap<String, Object> hashMap = new HashMap();
                        hashMap.put("star", test66s.get(i)[0]);
                        hashMap.put("end", test66s.get(i)[1]);
                        deviceShow.add(hashMap);
                    }
                    textViewMain.setText("执行完成");
                    simpleAdapter = new SimpleAdapter(context, deviceShow, R.layout.list_view_item_maps, new String[]{"star", "end"}, new int[]{R.id.star_addr, R.id.end_addr});
                    listView1.setAdapter(simpleAdapter);
                    listView1.setVisibility(View.VISIBLE);

                } catch (RuntimeException e) {
                    if (list != null) {
                        textViewMain.setText("找到符号规则的maps的条数 " + list.size() + e.getMessage());
                    } else {
                        textViewMain.setText(e.getMessage());
                    }

                }

            }
        });
        listView1.setOnItemClickListener(new GameModel.OnItemClick());
    }
    class OnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView textViewStar = view.findViewById(R.id.star_addr);
            String starAddr = textViewStar.getText().toString();
            TextView textViewEnd = view.findViewById(R.id.end_addr);
            String endAddr = textViewEnd.getText().toString();
            textViewMain.setText("请判断");
            onClickMapsItem = new String[]{starAddr, endAddr};
            long start = Long.parseLong(test66s.get(i)[0],16);
            long sizeOff = Long.parseLong(test66s.get(i)[2]);
            head = start+sizeOff-tankeFeature.getBaseCharacter().getIndex(); //这个是绝对的不变的字符的数
            long addrLong = head+tankeFeature.getSkillData()[test].getOffSize();
            String addr1 = String.format("%016x",addrLong);
            MapsTools.fastWrite1(pid,"0x"+addr1,0x40);

            if (starAddr != null) {
                onClickStar = starAddr;
            }
            if (endAddr != null) {

                onClickEnd = endAddr;
            }
            textViewMain.setText(" 0x" + onClickStar + " 0x" + onClickEnd);
        }
    }
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            if (bikeData != null && bikeData instanceof BikeData1) {
                BikeData1 bikeData1 = (BikeData1) bikeData;
                int Cadence = bikeData1.getInstantaneousCadence();
                Log.d("liujunjie", "runnable1线程正在刷新数据: " + Cadence);
                int countKM = bikeData1.getTotalDistancePresentUint();
                if (verticalProgress.getmProgress() > (countKM % 100)) {
                    vertical(countKM, true);
                } else {
                    vertical(countKM, false);
                }
                textViewMain.setText("当前踏频:" + Cadence);
            }
            handler.postDelayed(runnable1, 1 * 1000);

        }
        public void vertical(int countKM, boolean iso) {
            int variable = countKM % 100; //这个是需要添加到的值
            if (iso) {
                for (int k = verticalProgress.getmProgress(); k <100 ; k++) {
                    verticalProgress.setProgress(k);
                }
                for (int i = 0; i < variable; i++) {
                    verticalProgress.setProgress(i);
                }
                //刷新技能
                goldenBody(00);
            } else {
                int star = verticalProgress.getmProgress();
                for (int j = 0; j < variable - star; j++) {
                    verticalProgress.setProgress(star + j);
                }
            }
        }
    };
    /**
     * 这个方法是测试用到的，过时了*/
    @Deprecated
    public void goldenBody() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int ppp1 = random.nextInt(tankeFeature.getSkillData().length);
                SkillData skillData = tankeFeature.getSkillData()[ppp1];
                long addr = skillData.getPhysicalAddress();
                String addr1 = String.format("%016x",addr);
                MapsTools.fastWrite1(pid,addr1 , skillData.getValue());
                Log.d("技能", "goldenBody:新增 "+skillData.getName());
                textView2.setText("获得：" + skillData.getName() + "技能10秒");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MapsTools.fastWrite1(pid, addr1, skillData.getDefaultValue());
                        Log.d("技能", "run: 消失"+skillData.getName());
                        textView2.setText("技能消失");
                    }
                }, 5 * 1000);
                handler.postDelayed(this,6*1000);
            }

        },10*1000);

    }
    public void goldenBody(int ppp) {
        int ppp1 = random.nextInt(tankeFeature.getSkillData().length);
        SkillData skillData = tankeFeature.getSkillData()[ppp1];
        long addr = skillData.getPhysicalAddress();
        String addr1 = String.format("%016x",addr);
        MapsTools.fastWrite1(pid,addr1 , skillData.getValue());
        Log.d("技能", "goldenBody:新增 "+skillData.getName());
        textView2.setText("获得：" + skillData.getName() + "技能10秒");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MapsTools.fastWrite1(pid, addr1, skillData.getDefaultValue());
                Log.d("技能", "run: 消失"+skillData.getName());
                textView2.setText("技能消失");
            }
        }, 10 * 1000);
    }
    public static void upData(Bike c) {
        bikeData = c;
    }


    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public BaseCharacter getBaseCharacter() {
        return baseCharacter;
    }

    public void setBaseCharacter(BaseCharacter baseCharacter) {
        this.baseCharacter = baseCharacter;
    }

    public SkillData[] getSkillDataList() {
        return skillDataList;
    }

    public void setSkillDataList(SkillData[] skillDataList) {
        this.skillDataList = skillDataList;
    }


    public GameModel(String gameName, BaseCharacter baseCharacter, SkillData[] skillDataList,Context context) {
        this.gameName = gameName;
        this.baseCharacter = baseCharacter;
        this.skillDataList = skillDataList;
        this.context = context;

    }
    public GameModel(TankeFeature tankeFeature,Context context,int test){

        this.tankeFeature = tankeFeature;
        this.context = context;
        this.test = test;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

