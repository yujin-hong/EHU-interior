package com.yujinhong.myapplication2.ui.designwrite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DesignwriteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DesignwriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}