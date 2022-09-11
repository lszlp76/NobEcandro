package com.lszlp.nobec.ui.pharmacyOnDutyList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PharmacyOnDutyListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PharmacyOnDutyListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}