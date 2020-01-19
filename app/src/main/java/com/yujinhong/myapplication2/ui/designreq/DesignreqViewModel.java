package com.yujinhong.myapplication2.ui.designreq;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DesignreqViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DesignreqViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is designreq fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}