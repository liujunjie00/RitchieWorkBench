package com.ritchie.readandwrite;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ViewFragment extends View implements View.OnClickListener  {
    private Context context;
    private View view;
    private Button button;
    private EditText editText;
    private TextView textView;
    public ViewFragment(Context context) {
        super(context);
        this.context = context;
        //加载悬浮窗布局

        button = view.findViewById(R.id.button1);
        button.setOnClickListener(this);
    }

    public ViewFragment(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewFragment(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewFragment(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                Log.d("liujunjie","我被按压了");

        }

    }
}
