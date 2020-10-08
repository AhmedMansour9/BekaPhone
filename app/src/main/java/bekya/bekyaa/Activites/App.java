package bekya.bekyaa.Activites;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import bekya.bekyaa.ChangeLanguage;
import com.yariksoffice.lingver.Lingver;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
         Lingver.init(this, ChangeLanguage.getLanguage(this));

    }
}
