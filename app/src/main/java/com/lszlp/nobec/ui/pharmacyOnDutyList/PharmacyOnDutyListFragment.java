package com.lszlp.nobec.ui.pharmacyOnDutyList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lszlp.nobec.MainActivity;
import com.lszlp.nobec.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PharmacyOnDutyListFragment extends Fragment {

    private FragmentDashboardBinding binding;
    ArrayList<PharmacyModel> pharmacyModels;
    private String  BASE_URL ="https://github.com/lszlp76/jsons/tree/main/";
    Retrofit retrofit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PharmacyOnDutyListViewModel pharmacyOnDutyListViewModel =
                new ViewModelProvider(this).get(PharmacyOnDutyListViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        pharmacyOnDutyListViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);




        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Gson gson = new GsonBuilder().setLenient().create(); // create GSON
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))// Factory converts  JSON to GSON
                .build()
        ;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void  LoadData(){
        PharmacyAPI pharmacyAPI = retrofit.create(PharmacyAPI.class); // create service
        Call<List<PharmacyModel>> call = pharmacyAPI.getData();
        call.enqueue(new Callback<List<PharmacyModel>>() {
            @Override
            public void onResponse(Call<List<PharmacyModel>> call, Response<List<PharmacyModel>> response) {
                if (response.isSuccessful()){
                    List<PharmacyModel> responseList = response.body();
                    pharmacyModels = new ArrayList<>(responseList);

                    for ( PharmacyModel pharmacyModel: pharmacyModels){
                        System.out.println(pharmacyModel);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PharmacyModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}

/*
https://www.nosyapi.com/apiv2/pharmacyLink?apikey=x58j5AeEFXqvorUFG7X3o5QzGXqcThj8ncFGre0nucdVQYsuZKgfZ8ZKUf8y&city=bursa&county=mudanya
 */