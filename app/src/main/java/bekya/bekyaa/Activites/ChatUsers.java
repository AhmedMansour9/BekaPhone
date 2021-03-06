package bekya.bekyaa.Activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

import bekya.bekyaa.MyVolley;
import bekya.bekyaa.R;
import bekya.bekyaa.adapter.Messages;
import bekya.bekyaa.Model.meesage;
import bekya.bekyaa.tokenid.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class ChatUsers extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    DatabaseReference datamsg;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String Msg;
    RecyclerView recyclerView;
    bekya.bekyaa.adapter.Messages mAdapter;
    List<meesage> moviesList=new ArrayList<>();
    public   static int totalnumber=10;
    private int itempostion=0;
    private int padge=1;
    private String key="";
    private String prekey="";
    LinearLayoutManager linearLayoutManager;
    ValueEventListener value;
    DatabaseReference dat;
    public static String image;
    TextView T_User,T_blockedhim,T_blockyou;
    ImageView imgefriend,sendmessage;
    EditText Messages;
    String token,Socialuser,MySocial;
    String tokenadmin;
    String TokenUser,UserName,MyName;
    FirebaseAuth mAuth;
     Button Btn_Block,Btn_UnBlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_users);
        init();
        if(UserName.equals("Admin")){
            Btn_Block.setVisibility(View.INVISIBLE);
            Btn_UnBlock.setVisibility(View.INVISIBLE);
            T_blockedhim.setVisibility(View.INVISIBLE);
            T_blockyou.setVisibility(View.INVISIBLE);
        }else {
            CheckedBlocked();
        }
        Block();
        UnBlocked();
        Recyclview();
        SendMeesges();
        loadesmassg();
        SwipRefresh();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                padge++;

                itempostion=0;
                loademoresmassg();
            }
        });


    }

    private void UnBlocked() {

        Btn_UnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("block_list");
                databaseReference.child(MySocial).child(Socialuser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            dataSnapshot.getRef().removeValue();
                            DatabaseReference databaseReferen = FirebaseDatabase.getInstance().getReference("block_list");
                            databaseReferen.child(Socialuser).child(MySocial).removeValue();
                            T_blockedhim.setVisibility(View.GONE);
                            Messages.setVisibility(View.VISIBLE);
                            sendmessage.setVisibility(View.VISIBLE);
                            Btn_Block.setVisibility(View.VISIBLE);
                            Btn_UnBlock.setVisibility(View.INVISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    private void Block() {
      Btn_Block.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("block_list");
              HashMap<String,String> hashMap=new HashMap<>();
              hashMap.put("type","me");
              databaseReference.child(MySocial).child(Socialuser).setValue(hashMap);
              HashMap<String,String> hashMap2=new HashMap<>();
              hashMap2.put("type","to");
              databaseReference.child(Socialuser).child(MySocial).setValue(hashMap2);

          }
      });
    }

    private void CheckedBlocked() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("block_list");
        databaseReference.child(MySocial).child(Socialuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String Type=dataSnapshot.child("type").getValue().toString();
                        if(Type.equals("me")){
                            T_blockedhim.setVisibility(View.VISIBLE);
                            Messages.setVisibility(View.INVISIBLE);
                            sendmessage.setVisibility(View.INVISIBLE);
                            Btn_Block.setVisibility(View.INVISIBLE);
                            Btn_UnBlock.setVisibility(View.VISIBLE);
                        }else {
                            T_blockyou.setVisibility(View.VISIBLE);
                            Messages.setVisibility(View.INVISIBLE);
                            sendmessage.setVisibility(View.INVISIBLE);
                            Btn_Block.setVisibility(View.INVISIBLE);
                            Btn_UnBlock.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        T_blockedhim.setVisibility(View.INVISIBLE);
                        Messages.setVisibility(View.VISIBLE);
                        sendmessage.setVisibility(View.VISIBLE);
                        Btn_Block.setVisibility(View.VISIBLE);
                        Btn_UnBlock.setVisibility(View.INVISIBLE);


                    }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        T_blockyou=findViewById(R.id.T_blockyou);
        T_blockedhim=findViewById(R.id.T_blockedhim);
        Btn_UnBlock=findViewById(R.id.Btn_UnBlock);
        Btn_Block=findViewById(R.id.Btn_Block);
        mAuth = FirebaseAuth.getInstance();
        MyName = SharedPrefManager.getInstance(this).getMyName();
        sendmessage=findViewById(R.id.Btn_SendMessage);
        Messages=findViewById(R.id.E_Messsage);
        token = SharedPrefManager.getInstance(this).getDeviceToken();
        tokenadmin=SharedPrefManager.getInstance(this).getTokenAdmin();
//        data= FirebaseDatabase.getInstance().getReference("Users");
        datamsg= FirebaseDatabase.getInstance().getReference("ChatUsers");
        T_User=findViewById(R.id.T_User);
        UserName=getIntent().getStringExtra("user_name");
        T_User.setText(UserName);
        TokenUser=getIntent().getStringExtra("tokenuser");
        Socialuser=getIntent().getStringExtra("social");
        MySocial=SharedPrefManager.getInstance(ChatUsers.this).getSocialId();

    }

    public void loadesmassg(){
        DatabaseReference datams=datamsg.child(MySocial).child(Socialuser);

        Query mqery=datams.limitToLast(padge+totalnumber);

        mqery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                meesage a=dataSnapshot.getValue(meesage.class);

                itempostion++;
                if(itempostion==1){
                    String KEY=dataSnapshot.getKey();
                    key=KEY;
                    prekey=KEY;
                }
                moviesList.add(a);
                mAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(moviesList.size()-1);
                mSwipeRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void SendMessage(final String token, final String Msg){
        final String UserName=SharedPrefManager.getInstance(this).getMyName();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://wasalniegy.com//pushem2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ChatUsers.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ChatUsers.this, ""+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("title", UserName);


                params.put("message", Msg);
                params.put("email", token);
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);

    }
    public void SendMeesges(){
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenadmin=SharedPrefManager.getInstance(getBaseContext()).getTokenAdmin();
                String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).format(new Date());
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                format.setTimeZone(new SimpleTimeZone(0, "GMT"));


                Msg = Messages.getText().toString().trim();
                if (Msg != null && Msg.equals("")) {
                } else {
                    SendMessage(TokenUser,Msg);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Msg", Msg);
                    map.put("from", MySocial);
                    map.put("to", Socialuser);
                    map.put("from_token", token);
                    map.put("to_token", TokenUser);
                    map.put("recieved_from", MyName);
                    map.put("send_to", UserName);
                    map.put("date", date);
                    datamsg.child(MySocial).child(Socialuser).push().setValue(map);
                    datamsg.child(Socialuser).child(MySocial).push().setValue(map);
                    Messages.setText("");
                }
            }


        });


    }

    public void SwipRefresh(){
        mSwipeRefreshLayout = findViewById(R.id.swipe_Chat);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


    }
    private void loademoresmassg() {
        DatabaseReference datams=datamsg.child(token).child(TokenUser);

        Query mqery=datams.orderByKey().endAt(key).limitToLast(10);
        mqery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                meesage a=dataSnapshot.getValue(meesage.class);
                String KEY=dataSnapshot.getKey();
                if(!prekey.equals(KEY)) {

                    moviesList.add(itempostion++,a);

                }else {
                    prekey=key;
                }

                if(itempostion==1){

                    key=KEY;
                }
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                linearLayoutManager.scrollToPositionWithOffset(10,0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Recyclview(){
        recyclerView = findViewById(R.id.recycler_Chat);
        recyclerView.setHasFixedSize(true);
        mAdapter = new Messages(moviesList,this);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();
//        finish();



    }

    @Override
    public void onPause() {
        super.onPause();
//        finish();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();



    }

    @Override
    public void onRefresh() {

    }
}