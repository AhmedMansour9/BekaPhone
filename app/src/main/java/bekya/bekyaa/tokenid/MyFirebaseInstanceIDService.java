package bekya.bekyaa.tokenid;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;


/**
 * Created by HP on 14/04/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token is ", FirebaseInstanceId.getInstance().getToken());

        Log.d(TAG, "Refreshed token: " + refreshedToken);

        storeToken(refreshedToken);

    }


    private void storeToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);

    }
}
