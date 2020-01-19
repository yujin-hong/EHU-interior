package com.yujinhong.myapplication2.ui.designs;

import android.content.ContextWrapper;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yujinhong.myapplication2.DesignData;
import com.yujinhong.myapplication2.ListViewAdapter;
import com.yujinhong.myapplication2.Main6Activity;
import com.yujinhong.myapplication2.R;
import com.yujinhong.myapplication2.UnityPlayerActivity;
import com.yujinhong.myapplication2.ui.tools.ToolsViewModel;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.yujinhong.myapplication2.Main6Activity.getBitmapFromDrawable;

import static com.yujinhong.myapplication2.Main6Activity.getBitmapFromDrawable;
import static com.yujinhong.myapplication2.Main6Activity.mDrawerToggle;


public class DesignFragment extends Fragment implements View.OnClickListener {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    private ListView listView;
    private ListViewAdapter adapter;
    //private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();


    private ToolsViewModel designViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        designViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mydesign, container, false);
        Main6Activity.title.setText("내 인테리어 디자인");

        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id);
        Log.i("device id=",deviceId);

        ImageView btn  = (ImageView) root.findViewById(R.id.goUnitybtn);
        btn.setOnClickListener(this);


        listView = (ListView) root.findViewById(R.id.listviewmsg);
        initDatabase();
        adapter = new ListViewAdapter();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new AzrayList<String>());
        listView.setAdapter(adapter);
        mReference = mDatabase.getReference("Designs").child(deviceId); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //adapter.clear();
                adapter.clearItem();
                Log.i("msg2=","onDataChange");
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    // child 내에 있는 데이터만큼 반복합니다.
                    DesignData msg3 = messageData.getValue(DesignData.class);
                    String title = msg3.getTitle();
                    String address = msg3.getAddress();
                    String date = msg3.getDate();
                    Drawable myDrawable = new BitmapDrawable(getResources(), getActivity().getExternalFilesDir(null).getAbsoluteFile()+"/"+title+".png");
                    String msg2 = messageData.getValue().toString();
                    Log.i("msg2=",msg2);
                    //Array.add(msg2);
                    //adapter.add(msg2);
                    adapter.addItem(myDrawable, title, address, date);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                DesignData item = (DesignData) parent.getItemAtPosition(position);
                String titlecl = item.getTitle();
                String addresscl = item.getAddress();
                String datecl = item.getDate();
                String dirPath = getActivity().getExternalFilesDir(null).getAbsolutePath();
                String filename = "start.txt";
                File dir = new File(dirPath);
                File file2 = new File(dir, filename);
                try{
                    BufferedWriter bwr = new BufferedWriter(new FileWriter(file2));
                    bwr.flush();
                    bwr.write("1");
                    bwr.close();
                }
                catch(Exception e){

                }

                String dirPath2 = getActivity().getExternalFilesDir(null).getAbsolutePath();
                String filename2 = "title.txt";
                File dir2 = new File(dirPath2);
                File file3 = new File(dir2, filename2);
                try{
                    BufferedWriter bwr = new BufferedWriter(new FileWriter(file3));
                    bwr.flush();
                    bwr.write(titlecl);
                    bwr.close();
                }
                catch(Exception e){

                }

                Log.i("onclick", titlecl+addresscl+datecl);

                readActorFile(titlecl);
                readWallFile(titlecl);
                readFloorFile(titlecl);
                readArFile(titlecl);

                Intent intent = new Intent();
                intent.setClass(getActivity(), UnityPlayerActivity.class);
                startActivity(intent);

            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goUnitybtn:

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
        }
    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Designs");
        //mReference.child("lists").setValue("check");
        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }


    private void readActorFile(String title){
        Log.i("here", getActivity().getExternalFilesDir(null).getAbsolutePath());
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            File file = new File(getActivity().getExternalFilesDir(null).getAbsoluteFile()+"/"+title+"actors.json");
            FileInputStream fis = new FileInputStream(file);//파일명
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str != null) {
                data.append(str + "\n");
                str = buffer.readLine();
            }
            buffer.close();
            String dirPath = getActivity().getExternalFilesDir(null).getAbsolutePath();
            String filename = "actors.json";
            File dir = new File(dirPath);
            File file2 = new File(dir, filename);
            BufferedWriter bwr = new BufferedWriter(new FileWriter(file2));
            bwr.flush();
            bwr.write(data.toString());
            bwr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readWallFile(String title){
        Log.i("here", getActivity().getExternalFilesDir(null).getAbsolutePath());
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            File file = new File(getActivity().getExternalFilesDir(null).getAbsoluteFile()+"/"+title+"wall.json");
            FileInputStream fis = new FileInputStream(file);//파일명
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str != null) {
                data.append(str + "\n");
                str = buffer.readLine();
            }
            buffer.close();
            String dirPath = getActivity().getExternalFilesDir(null).getAbsolutePath();
            String filename = "wall.json";
            File dir = new File(dirPath);
            File file2 = new File(dir, filename);
            BufferedWriter bwr = new BufferedWriter(new FileWriter(file2));
            bwr.flush();
            bwr.write(data.toString());
            bwr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFloorFile(String title){
        Log.i("here", getActivity().getExternalFilesDir(null).getAbsolutePath());
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            File file = new File(getActivity().getExternalFilesDir(null).getAbsoluteFile()+"/"+title+"floor.json");
            FileInputStream fis = new FileInputStream(file);//파일명
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str != null) {
                data.append(str + "\n");
                str = buffer.readLine();
            }
            buffer.close();
            String dirPath = getActivity().getExternalFilesDir(null).getAbsolutePath();
            String filename = "floor.json";
            File dir = new File(dirPath);
            File file2 = new File(dir, filename);
            BufferedWriter bwr = new BufferedWriter(new FileWriter(file2));
            bwr.flush();
            bwr.write(data.toString());
            bwr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readArFile(String title){
        Log.i("here", getActivity().getExternalFilesDir(null).getAbsolutePath());
        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            File file = new File(getActivity().getExternalFilesDir(null).getAbsoluteFile()+"/"+title+"aractors.json");
            FileInputStream fis = new FileInputStream(file);//파일명
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            while (str != null) {
                data.append(str + "\n");
                str = buffer.readLine();
            }
            buffer.close();
            String dirPath = getActivity().getExternalFilesDir(null).getAbsolutePath();
            String filename = "aractors.json";
            File dir = new File(dirPath);
            File file2 = new File(dir, filename);
            BufferedWriter bwr = new BufferedWriter(new FileWriter(file2));
            bwr.flush();
            bwr.write(data.toString());
            bwr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



}
