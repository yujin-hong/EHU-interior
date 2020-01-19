package com.yujinhong.myapplication2.ui.designreq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.yujinhong.myapplication2.Main6Activity;
import com.yujinhong.myapplication2.R;
import com.yujinhong.myapplication2.ui.designwrite.DesignwriteFragment;
import com.yujinhong.myapplication2.ui.gallery.GalleryFragment;

public class DesignreqFragment extends Fragment implements View.OnClickListener {

    private DesignreqViewModel designreqViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        designreqViewModel =
                ViewModelProviders.of(this).get(DesignreqViewModel.class);
        View root = inflater.inflate(R.layout.fragment_designreq, container, false);
        TextView v = (TextView) root.findViewById(R.id.write);
        v.setOnClickListener(this);
//        final TextView textView = root.findViewById(R.id.text_tools);
//        designreqViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Main6Activity.title.setText("디자인 의뢰 게시판");

        return root;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.write:
                DesignwriteFragment frag = new DesignwriteFragment();
                FragmentTransaction mFragmentTransaction = getFragmentManager()
                        .beginTransaction();
                mFragmentTransaction.replace(R.id.nav_host_fragment, frag);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
        }
    }
}