package com.lszlp.nobec.ui.settings;

import android.media.Image;

public class SettingsListModel {

    int settingCellText;
    int settingsIcon; // bundle içinde resimler integer olarak tutulur

public SettingsListModel (int settingCellText, int settingsIcon){
    this.settingCellText = settingCellText;
    this.settingsIcon = settingsIcon;
}

    public int getSettingCellText() {
        return settingCellText;
    }

    public void setSettingCellText(int settingCellText) {
        this.settingCellText = settingCellText;
    }

    public int getSettingsIcon() {
        return settingsIcon;
    }

    public void setSettingsIcon(int settingsIcon) {
        this.settingsIcon = settingsIcon;
    }

/*
Bunu kullanırken diziyi --> ArrayList<SettingsListModel> slm = new ArrayList<> olarak vermelisin.
 */

}
