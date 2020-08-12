package com.ariri.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//11 Agustus, 10117231, Ahmad Ripai Rizki, IF7
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        
    }

    public LiveData<String> getText() {
        return mText;
    }
}