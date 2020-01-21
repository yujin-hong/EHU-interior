package com.yujinhong.myapplication2.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.yujinhong.myapplication2.R;
import com.yujinhong.myapplication2.UnityPlayerActivity;
import com.yujinhong.myapplication2.ui.designreq.DesignreqFragment;
import com.yujinhong.myapplication2.ui.gallery.GalleryFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class AddFragment extends Fragment implements View.OnClickListener {

    private AddViewModel addViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                ViewModelProviders.of(this).get(AddViewModel.class);

        View root = inflater.inflate(R.layout.fragment_add, container, false);
        Button go_design_commission = (Button) root.findViewById(R.id.go_design_commission);
        Button go_design = (Button) root.findViewById(R.id.go_design);
        Button go_sigong_commission = (Button) root.findViewById(R.id.go_sigong_commission);

        go_design_commission.setOnClickListener(this);
        go_design.setOnClickListener(this);
        go_sigong_commission.setOnClickListener(this);

//        final TextView textView = root.findViewById(R.id.text_add);
//        addViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_design_commission:
                DesignreqFragment frag = new DesignreqFragment();
                FragmentTransaction mFragmentTransaction = getFragmentManager()
                        .beginTransaction();
                mFragmentTransaction.replace(R.id.nav_host_fragment, frag);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
            case R.id.go_design:
                String dirPath = getActivity().getExternalFilesDir(null).getAbsolutePath();
                String filename = "start.txt";
                File dir = new File(dirPath);
                File file2 = new File(dir, filename);
                try{
                    BufferedWriter bwr = new BufferedWriter(new FileWriter(file2));
                    bwr.flush();
                    bwr.write("0");
                    bwr.close();
                }
                catch(Exception e){
                }

                Intent intent = new Intent();
                intent.setClass(getActivity(), UnityPlayerActivity.class);
                startActivity(intent);
                Log.i("msg2=","next activity");
                break;
            case R.id.go_sigong_commission:
                GalleryFragment frag1 = new GalleryFragment();
                FragmentTransaction mFragmentTransaction1 = getFragmentManager()
                        .beginTransaction();
                mFragmentTransaction1.replace(R.id.nav_host_fragment, frag1);
                mFragmentTransaction1.addToBackStack(null);
                mFragmentTransaction1.commit();
                break;
        }
    }
}