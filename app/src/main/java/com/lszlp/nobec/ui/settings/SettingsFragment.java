package com.lszlp.nobec.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lszlp.nobec.R;
import com.lszlp.nobec.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
ArrayList<SettingsListModel> settingsListElement;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        settingsListElement = new ArrayList<SettingsListModel>();

        settingsListElement.add(new SettingsListModel(R.string.rate, R.drawable.ic_baseline_star_rate_24));
        settingsListElement.add(new SettingsListModel(R.string.satellite,R.drawable.ic_baseline_satellite_24));
        settingsListElement.add(new SettingsListModel(R.string.policy,R.drawable.ic_policy));
        settingsListElement.add(new SettingsListModel(R.string.about,R.drawable.ic_baseline_content_paste_24));

        binding.settingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingsRecyclerViewAdapter settingsRecyclerViewAdapter = new SettingsRecyclerViewAdapter(settingsListElement);
        binding.settingsRecyclerView.setAdapter(settingsRecyclerViewAdapter);

       // final TextView textView = binding.textNotifications;
       // settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}