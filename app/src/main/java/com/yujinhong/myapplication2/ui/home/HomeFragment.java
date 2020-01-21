package com.yujinhong.myapplication2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.yujinhong.myapplication2.Main6Activity;
import com.yujinhong.myapplication2.R;
import com.yujinhong.myapplication2.ui.designreq.DesignreqFragment;
import com.yujinhong.myapplication2.ui.designs.DesignFragment;
import com.yujinhong.myapplication2.ui.my.MyFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView goto_design = (TextView) root.findViewById(R.id.goto_design);
        TextView goto_mypage = (TextView) root.findViewById(R.id.goto_mypage);
        TextView goto_design_commission = (TextView) root.findViewById(R.id.goto_design_commission);

        goto_design.setOnClickListener(this);
        goto_mypage.setOnClickListener(this);
        goto_design_commission.setOnClickListener(this);
        Main6Activity.title.setText("홈");

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_design_commission:
                DesignreqFragment frag = new DesignreqFragment();
                FragmentTransaction mFragmentTransaction = getFragmentManager()
                        .beginTransaction();
                mFragmentTransaction.replace(R.id.nav_host_fragment, frag);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case R.id.goto_design:
                DesignFragment frag1 = new DesignFragment();
                FragmentTransaction mFragmentTransaction1 = getFragmentManager()
                        .beginTransaction();
                mFragmentTransaction1.replace(R.id.nav_host_fragment, frag1);
                mFragmentTransaction1.addToBackStack(null);
                mFragmentTransaction1.commit();
                break;
            case R.id.goto_mypage:
                MyFragment frag2 = new MyFragment();
                FragmentTransaction mFragmentTransaction2 = getFragmentManager()
                        .beginTransaction();
                mFragmentTransaction2.replace(R.id.nav_host_fragment, frag2);
                mFragmentTransaction2.addToBackStack(null);
                mFragmentTransaction2.commit();
                break;
        }
    }

}