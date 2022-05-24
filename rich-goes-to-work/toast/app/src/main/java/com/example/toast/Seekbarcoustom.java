package com.example.toast;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class Seekbarcoustom extends View implements View.OnTouchListener {
    public Seekbarcoustom(Context context) {
        super(context);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
