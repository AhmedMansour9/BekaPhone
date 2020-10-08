package bekya.bekyaa.Activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import bekya.bekyaa.ChangeLanguage;
import bekya.bekyaa.R;
import bekya.bekyaa.tokenid.SharedPrefManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;
    private ImageView imageLogo;
    private FirebaseAuth.AuthStateListener mAuthListener;
    VideoView vid;

    FirebaseAuth mAuth;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeLanguage.changeLang(this);
        setContentView(R.layout.activity_splash_screen);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(splash_screen.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                storeToken(token);


            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                mAuth = FirebaseAuth.getInstance();
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() != null) {
                            startActivity(new Intent(splash_screen.this, Home.class));
                            finish();
                        } else {
                            Intent i = new Intent(splash_screen.this, Login.class);
                            startActivity(i);

                            // close this activity
                            finish();

                        }
                    }
                };
                mAuth.addAuthStateListener(mAuthListener);
            }

        }, SPLASH_TIME_OUT);
    }
    private void storeToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);

    }

    public void playVideo() {
//        MediaController mediaController= new MediaController(this);
//        mediaController.setAnchorView(vid);

        String path = "android.resource://" +getPackageName()+"/"+R.raw.vedio;

        Uri u = Uri.parse(path);

        vid.setVideoURI(u);

        vid.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth!=null)
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAuth!=null)
            mAuth.removeAuthStateListener(mAuthListener);
    }
}

