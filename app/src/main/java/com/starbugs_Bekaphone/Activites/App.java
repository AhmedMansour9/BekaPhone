package com.starbugs_Bekaphone.Activites;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.starbugs_Bekaphone.ChangeLanguage;
import com.yariksoffice.lingver.Lingver;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
         Lingver.init(this, ChangeLanguage.getLanguage(this));

    }
}
