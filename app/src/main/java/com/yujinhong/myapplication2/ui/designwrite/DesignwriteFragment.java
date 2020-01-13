package com.yujinhong.myapplication2.ui.designwrite;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kakao.util.helper.log.Logger;
import com.yujinhong.myapplication2.Main6Activity;
import com.yujinhong.myapplication2.R;

import static com.yujinhong.myapplication2.Main6Activity.getBitmapFromDrawable;
import static com.yujinhong.myapplication2.Main6Activity.mDrawerToggle;

public class DesignwriteFragment extends Fragment {

    private DesignwriteViewModel DesignwriteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DesignwriteViewModel =
                ViewModelProviders.of(this).get(DesignwriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_designwrite, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        DesignwriteViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Main6Activity.title.setText("디자인 의뢰 글쓰기");
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable= getResources().getDrawable(R.drawable.ic_arrow_green);
        Bitmap bitmap = getBitmapFromDrawable(drawable);
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
        actionBar.setHomeAsUpIndicator(newdrawable);
        return root;
    }
}