package com.yujinhong.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class sharedPreferenceArrayList extends AppCompatActivity {
//    SharedPreferences loginInformation = this.getSharedPreferences("setting",0);;
//    SharedPreferences.Editor editor = loginInformation.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static <E> List<E> getJsonArrayList(SharedPreferences mSharedPref, String strShareKey) {
        String json = mSharedPref.getString(strShareKey, null);
        List<E> valueList = new ArrayList<>();

        if(json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++) {
                    valueList.add((E) jsonArray.opt(i));
                }
            } catch (JSONException e) {
                Logger.e("TEST", e.toString());
            }
        }
        return valueList;
    }

    public static <E> void setJsonArrayList(SharedPreferences mSharedPref, String strShareKey, E newValue) {
        List<E> prevList = getJsonArrayList(mSharedPref, strShareKey);
        JSONArray jsonArray = new JSONArray();
        SharedPreferences.Editor editor = mSharedPref.edit();

        for (E value : prevList) {
            jsonArray.put(value);
        }
        jsonArray.put(newValue);
        editor.putString(strShareKey, jsonArray.toString()).commit();

    }
}
