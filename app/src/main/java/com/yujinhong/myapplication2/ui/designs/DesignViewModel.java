package com.yujinhong.myapplication2.ui.designs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class DesignViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public DesignViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is design fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
