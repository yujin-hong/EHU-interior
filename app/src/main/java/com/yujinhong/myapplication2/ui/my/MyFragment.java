package com.yujinhong.myapplication2.ui.my;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.yujinhong.myapplication2.Main6Activity;
import com.yujinhong.myapplication2.R;

public class MyFragment extends Fragment {

    private MyViewModel myViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myViewModel =
                ViewModelProviders.of(this).get(MyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        final TextView textView = root.findViewById(R.id.text_my);
        myViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        Main6Activity.title.setText("마이 페이지");

//        Drawable drawable= getResources().getDrawable(R.mipmap.ic_launcher);
//        Bitmap bitmap = Main6Activity.getBitmapFromDrawable(drawable);
//        final Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
//        Main6Activity.toolbar.setNavigationIcon(newdrawable);
        return root;
    }
}