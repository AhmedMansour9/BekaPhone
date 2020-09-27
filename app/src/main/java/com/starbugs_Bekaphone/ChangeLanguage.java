package com.starbugs_Bekaphone;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeLanguage {

      public static void changeLang(Context context) {
          SharedPreferences shared = context.getSharedPreferences("Language", AppCompatActivity.MODE_PRIVATE);
          String Lan = shared.getString("Lann", null);
          if (Lan != null) {
              Locale locale = new Locale(Lan);
              Locale.setDefault(locale);
              Configuration config = new Configuration();
              config.locale = locale;
              context.getResources().updateConfiguration(
                      config,
                      context.getResources().getDisplayMetrics()
              );
          }
      }
          public static  String getLanguage (Context context){
              String DeviceLang = "";
              SharedPreferences shared = context.getSharedPreferences("Language", AppCompatActivity.MODE_PRIVATE);
              String Lan = shared.getString("Lann", null);
              if (Lan != null) {
                  DeviceLang = Lan;
              } else {
                  DeviceLang = Locale.getDefault().getLanguage();

              }
              return DeviceLang;
          }

}
