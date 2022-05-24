package com.liqi.test3399.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTouch extends Fragment {

    public FragmentTouch(int touch_fragment) {
        super(touch_fragment);
    }

    public FragmentTouch() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
