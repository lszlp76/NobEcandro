package com.lszlp.nobec.ui.nearPharmacy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearPharmacyViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NearPharmacyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Near Pharmcy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}