package com.starbugs_Bekaphone.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.starbugs_Bekaphone.ChangeLanguage;
import com.starbugs_Bekaphone.Fragments.Categories;
import com.starbugs_Bekaphone.Fragments.Chat;
import com.starbugs_Bekaphone.Common.Common;
import com.starbugs_Bekaphone.Fragments.ContactUs;
import com.starbugs_Bekaphone.Fragments.Language;
import com.starbugs_Bekaphone.Fragments.MyChat;
import com.starbugs_Bekaphone.R;
import com.starbugs_Bekaphone.Fragments.myposts;
import com.starbugs_Bekaphone.tokenid.SharedPrefManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Home extends AppCompatActivity
        implements   NavigationView.OnNavigationItemSelectedListener {
    Fragment fr;
    private int mCurrentSelectedPosition = 0;
   // FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    public static String Id="";
    public static String Name="";
    public static TextView T_Government,T_Title;
    public static RelativeLayout Rela_Govern;
    public static String token,Language;
    public static String Social_Id;
    public static Toolbar toolbar;
    private static final String URL_REGISTER_DEVICE = "http://wasalniegy.com/RegisterDevice.php";
    public static Boolean Visablty;
    private InterstitialAd mInterstitialAd;
    FirebaseAuth mAuth;
    SharedPreferences shared;
    private ImageView Img_twitter,Img_instegram,Img_facebook;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            // Wait 60 seconds
            mHandler.postDelayed(this, 100000);

            // Show Ad
            showInterstitial();

        }
    };
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/whatsappbold.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
//        shared=getSharedPreferences("Language",MODE_PRIVATE);
//        String Lan=shared.getString("Lann",null);
//        if(Lan!=null) {
//            Locale locale = new Locale(Lan);
//            Locale.setDefault(locale);
//            Configuration config = new Configuration();
//            config.locale = locale;
//            getBaseContext().getResources().updateConfiguration(config,
//                    getBaseContext().getResources().getDisplayMetrics());
//        }
        setContentView(R.layout.activity_home);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Home.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                storeToken(token);
                sendTokenToServer();

            }
        });
        mInterstitialAd = newInterstitialAd();
        mAuth = FirebaseAuth.getInstance();
        Language=ChangeLanguage.getLanguage(this);
        loadInterstitial();
        showInterstitial();
           init();
         SendTokenFirebase();
        GetUserNameFirebase();
        CheckedBlocked();
        openFacebook();
//        openInstegram();
//        openTwitter();




//        DatabaseReference da6=FirebaseDatabase.getInstance().getReference().child("Category").push();
//        da6.child("cat_ar").setValue("ريلمي");
//        da6.child("cat_en").setValue("Realme");
//        String key6 =da6.getKey();
//        da6.child("key").setValue(key6);

//        DatabaseReference da7=FirebaseDatabase.getInstance().getReference().child("Category").push();
//        da7.child("cat_ar").setValue("تكنو");
//        da7.child("cat_en").setValue("Techno");
//        String key7 =da7.getKey();
//        da7.child("key").setValue(key7);

//        DatabaseReference da7=FirebaseDatabase.getInstance().getReference().child("Sub_Category").push();
//        da7.child("cat_ar").setValue("تليفونات");
//        da7.child("cat_en").setValue("Phones");
//        da7.child("key").setValue("-MIA0PqpAStDAnBwK0HG");
//        String key7 =da7.getKey();
//        da7.child("sub_key").setValue(key7);
//
//
//        DatabaseReference da8=FirebaseDatabase.getInstance().getReference().child("Sub_Category").push();
//        da8.child("cat_ar").setValue("اكسسويرات");
//        da8.child("cat_en").setValue("Accessories");
//        da8.child("key").setValue("-MIA0PqpAStDAnBwK0HG");
//        String key8 =da8.getKey();
//        da8.child("sub_key").setValue(key8);
//
//        DatabaseReference da9=FirebaseDatabase.getInstance().getReference().child("Sub_Category").push();
//        da9.child("cat_ar").setValue("قطع غيار");
//        da9.child("cat_en").setValue("Spare Parts");
//        da9.child("key").setValue("-MIA0PqpAStDAnBwK0HG");
//        String key9 =da9.getKey();
//        da9.child("sub_key").setValue(key9);


    }

    private void openFacebook() {
        Img_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bekya.bns/"));
                startActivity(intent);
            }
        });
    }
    private void openTwitter() {
        Img_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/Tempomena"));
                startActivity(intent);
            }
        });
    }
    private void openInstegram() {
        Img_instegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/p/CB8KjbfHz-l/?igshid=9mcjzamyxphj"));
                startActivity(intent);
            }
        });
    }
    private void CheckedBlocked() {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("block_app");
        databaseReference.child( mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mAuth.signOut();
                    Intent intent=new Intent(Home.this,Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void storeToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);

    }

    private void GetUserNameFirebase(){
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.orderByChild("id").equalTo(mAuth.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()) {
                    if(dataSnapshot.child("username").getValue().toString()!=null){
                        String username = dataSnapshot.child("username").getValue().toString();
                        SharedPrefManager.getInstance(Home.this).saveMyName(username);

                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendTokenFirebase(){
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("admin");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String token = dataSnapshot.child("token").getValue().toString();
                    SharedPrefManager.getInstance(Home.this).saveTokenAdmin(token);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start showing Ad in every 60 seconds
        //when activity is visible to the user
        mHandler = new Handler();
        //mHandler.post(mRunnable);

        // Run first add after 20 seconds
        mHandler.postDelayed(mRunnable,8*6000);


    }
    protected void onStop() {
        super.onStop();
        // Stop showing Ad when activity is not visible anymore
        mHandler.removeCallbacks(mRunnable);
    }
    private void loadInterstitial() {
//        mInterstitialAd.setAdUnitId("ca-app-pub-1430161852443923/2843887354");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    private InterstitialAd newInterstitialAd() {

        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1430161852443923/2843887354");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
//                mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {

                // Reload ad so it can be ready to be show to the user next time
                mInterstitialAd = newInterstitialAd();
                loadInterstitial();

            }
        });
        return interstitialAd;

    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
//            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();
        }
    }


    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("الصفحة الرئيسية");
        setSupportActionBar(toolbar);
        T_Title=findViewById(R.id.T_Title);
        Img_facebook=findViewById(R.id.Img_facebook);

//        toolbar.setLogo(R.mipmap.actionbarlogotw);
        //Initialize Firebase
        if(getIntent().getStringExtra("id")!=null) {
            Id = getIntent().getStringExtra("id");
        }
        if(getIntent().getStringExtra("name")!=null) {
            Name = getIntent().getStringExtra("name");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        // txtFullName = (TextView)headerView.findViewById(R.id.txtFullName);
        T_Government=findViewById(R.id.T_Government);
        Rela_Govern=findViewById(R.id.Rela_Govern);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        Rela_Govern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Cities.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (Visablty) {
                if (fr instanceof Categories) {
                    super.onBackPressed();

                } else if (fr instanceof myposts) {
                    BackToHome();
                } else if (fr instanceof MyChat) {
                    BackToHome();
                } else if (fr instanceof Chat) {
                    BackToHome();
                }
                else if (fr instanceof Language) {
                    BackToHome();
                }
                else {
                    super.onBackPressed();
                }
            }
            else {

                super.onBackPressed();
            }
            }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    private void BackToHome()
    {
        fr = new Categories();
        if(fr !=null)
        {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.flContent,fr,fr.getTag()).commit();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.nav_products:
                mCurrentSelectedPosition = 0;
                fr = new Categories();
                break;
            case R.id.posts:
                mCurrentSelectedPosition = 1;

                fr = new myposts();
                break;
            case R.id.mychat:
                mCurrentSelectedPosition = 2;

                fr = new MyChat();
                break;

//            case R.id.chat:
//                mCurrentSelectedPosition = 3;
//
//                fr = new ContactUs();
//                break;

//            case R.id.about:
//                mCurrentSelectedPosition = 4;
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle(String.format("%1$s", getString(R.string.app_name)));
//                builder.setMessage(getResources().getText(R.string.aboutus));
//                builder.setPositiveButton("ok", null);
//                builder.setIcon(R.mipmap.bekyaalogo);
//                AlertDialog welcomeAlert = builder.create();
//                welcomeAlert.show();


            case R.id.nav_language:
                mCurrentSelectedPosition = 4;

                fr = new Language();
                break;

//            case R.id.nav_help:
//                mCurrentSelectedPosition = 5;
//
//               startActivity(new Intent(Home.this,StepperWizardLight.class));
//                break;

            case R.id.log_out:

                mCurrentSelectedPosition = 5;
                mAuth.signOut();
                Intent intent=new Intent(Home.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
              break;


            default:
                mCurrentSelectedPosition = 0;

        }
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);


        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent,fr);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSettingdialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("ضبط الإشعارات");
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_setting= inflater.inflate(R.layout.setting_layout,null);
        final CheckBox chk_subscribe =layout_setting.findViewById(R.id.chk_new);
        //remember state of checkbox
        Paper.init(this);
        String isSubscribe = Paper.book().read("sub_new","true");
        if(isSubscribe==null|| TextUtils.isEmpty(isSubscribe) || isSubscribe.equals("false"))
            chk_subscribe.setChecked(false);
        else
            chk_subscribe.setChecked(true);

        alertDialog.setView(layout_setting);
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(chk_subscribe.isChecked())
                {
                    FirebaseMessaging.getInstance().subscribeToTopic(Common.topicName);
                    Paper.book().write("sub_new" , "true");
                }
                else
                {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Common.topicName);
                    Paper.book().write("sub_new" , "false");
                }

            }
        });
        alertDialog.show();


    }

    private void sendTokenToServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", token);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



























//


//        DatabaseReference da5=FirebaseDatabase.getInstance().getReference().child("Sub_Category").push();
//        da5.child("cat_ar").setValue("فيزياء");
//        da5.child("cat_en").setValue("Physics");
//        da5.child("key").setValue("-M7-z6AVf5xPq30GChFL");
//        String key =da5.getKey();
//        da5.child("sub_key").setValue(key);
//
//        DatabaseReference da6=FirebaseDatabase.getInstance().getReference().child("Sub_Category").push();
//        da6.child("cat_ar").setValue("دين");
//        da6.child("cat_en").setValue("Religion");
//        da6.child("key").setValue("-M7-z6AVf5xPq30GChFL");
//        String key6 =da6.getKey();
//        da6.child("sub_key").setValue(key6);
//
//        DatabaseReference da3=FirebaseDatabase.getInstance().getReference().child("Sub_Category").push();
//        da3.child("cat_ar").setValue("اجتماعيات");
//        da3.child("cat_en").setValue("Social Studies");
//        da3.child("key").setValue("-M7-z6AVf5xPq30GChFL");
//        String key3 =da3.getKey();
//        da3.child("sub_key").setValue(key3);


//
//
//



    //    DatabaseReference da5=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        da5.child("id").setValue("22");
//        da5.child("name").setValue("Marsa Matruh");
//        da5.child("name_ar").setValue("مطروح");
//        da5.child("cu").setValue("EGY");
//
//    DatabaseReference dat=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat.child("id").setValue("23");
//        dat.child("name").setValue("Monufia");
//        dat.child("name_ar").setValue("المنوقية");
//        dat.child("cu").setValue("EGY");
//
//    DatabaseReference datsss=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        datsss.child("id").setValue("24");
//        datsss.child("name").setValue("New Valley");
//        datsss.child("name_ar").setValue("الوادي الجديد");
//        datsss.child("cu").setValue("EGY");
//
//    DatabaseReference dat3=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat3.child("id").setValue("25");
//        dat3.child("name").setValue("6of Octabor");
//        dat3.child("name_ar").setValue("السادس من اكتوبر");
//        dat3.child("cu").setValue("EGY");
//
//    DatabaseReference dat4=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat4.child("id").setValue("26");
//        dat4.child("name").setValue("North Sinai");
//        dat4.child("name_ar").setValue("شمال سيناء");
//        dat4.child("cu").setValue("EGY");
//
//    DatabaseReference dat5=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat5.child("id").setValue("27");
//        dat5.child("name").setValue("South Sinai");
//        dat5.child("name_ar").setValue("جنوب سيناء");
//        dat5.child("cu").setValue("EGY");
//
//    DatabaseReference dat6=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat6.child("id").setValue("28");
//        dat6.child("name").setValue("Helwan");
//        dat6.child("name_ar").setValue("حلوان");
//        dat6.child("cu").setValue("EGY");

//    DatabaseReference da7=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        da7.child("id").setValue("18");
//        da7.child("name").setValue("Gharbia");
//        da7.child("name_ar").setValue("الغربية");
//        da7.child("cu").setValue("EGY");
//
//    DatabaseReference dat8=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat8.child("id").setValue("19");
//        dat8.child("name").setValue("Faiyum");
//        dat8.child("name_ar").setValue("القيوم");
//        dat8.child("cu").setValue("EGY");
//
//    DatabaseReference dat9=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat9.child("id").setValue("20");
//        dat9.child("name").setValue("Qena");
//        dat9.child("name_ar").setValue("قنا");
//        dat9.child("cu").setValue("EGY");
//
//    DatabaseReference dat10=FirebaseDatabase.getInstance().getReference().child("Cities").push();
//        dat10.child("id").setValue("21");
//        dat10.child("name").setValue("Kafr El Sheikh");
//        dat10.child("name_ar").setValue("كفر الشيخ");
//        dat10.child("cu").setValue("EGY");
//DatabaseReference da5=FirebaseDatabase.getInstance().getReference().child("Category").push();
//        da5.child("cat_ar").setValue("انواع اخري");
//        da5.child("cat_en").setValue("Others");
//    String key =da5.getKey();
//        da5.child("key").setValue(key);


}
