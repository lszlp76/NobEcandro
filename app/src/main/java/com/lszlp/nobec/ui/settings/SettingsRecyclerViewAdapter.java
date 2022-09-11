package com.lszlp.nobec.ui.settings;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lszlp.nobec.R;
import com.lszlp.nobec.databinding.SettingsRecycleRowBinding;

import java.util.ArrayList;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.SettingsViewHolder> {


    // ArrayList<SettingsListModel> slm ; olarak yarat
    // sonra bunun konstraktırını oluşturu
    //bu sayede bu adapter i çağırdın zaman içine settings list isteyecek. Doğrudan buraya getirecek.

    ArrayList<SettingsListModel> slm;

    public SettingsRecyclerViewAdapter(ArrayList<SettingsListModel> slm) {
        this.slm = slm;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.settings_recycle_row, parent, false);
        SettingsRecycleRowBinding settingsRecycleRowBinding = SettingsRecycleRowBinding.inflate(layoutInflater, parent, false);

        return new SettingsViewHolder(settingsRecycleRowBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, @SuppressLint("RecyclerView") int position) {
//layout içinde neleri göstermek istiyorsan burada göstereceksin
        // bu nedenle recycler_row yarat
        holder.binding.settingsRecyclerViewtextView.setText(slm.get(position).getSettingCellText());
        holder.binding.settingsRecyclerViewImageView.setImageResource(slm.get(position).getSettingsIcon());
        holder.binding.getRoot().setBackgroundColor( 45);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        //to rate method
                        System.out.println("Rate method");
                        break;
                    case 1:
                        // to satellite signal method
                        System.out.println("SATELİTE method");
                        break;
                    case 2:
                        //to policy
                        System.out.println("Policy method");
                        break;
                    case 3:
                        // to about
                        System.out.println("About something");
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return slm.size();
    }

    public class SettingsViewHolder extends RecyclerView.ViewHolder {

        private SettingsRecycleRowBinding binding;

        public SettingsViewHolder(SettingsRecycleRowBinding binding) {
            super(binding.getRoot()); // binding e görünüm alıyornew
            this.binding = binding;
        }
    }

}
