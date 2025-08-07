package com.automation.businessLayer;

import com.automation.screens.BackgroundScreen;
import com.automation.screens.DashboardScreen;

public class BackgroundBL {

    private BackgroundScreen backgroundScreen;

    public BackgroundBL() {
        this.backgroundScreen = new BackgroundScreen();
    }

    public void openApplication(String url) {
        System.out.println("BL: Opening application at " + url);
        backgroundScreen.openApplication(url);
        System.out.println("BL: Application opened successfully");
    }
}
