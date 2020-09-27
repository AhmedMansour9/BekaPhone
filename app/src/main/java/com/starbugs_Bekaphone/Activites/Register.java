package com.starbugs_Bekaphone.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.starbugs_Bekaphone.R;
import com.starbugs_Bekaphone.tokenid.SharedPrefManager;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Register extends AppCompatActivity {
    EditText E_FirstName,E_LastName,E_LastNameCompany,E_FirstNameCompany,E_FullName,E_Years,E_Major,E_Location,E_CompanyName,E_Phone,E_PhoneCompany,E_PasswordCompany,E_EmailCompany,E_Email,E_Password,E_FullNameCompany,E_Company;
    TextView T_Company,T_Indvidual;
    FirebaseAuth auth;
    private Button Btn_Register;
    private ProgressBar progressBarRegister;
    String username,Type;
    Boolean UserNameStatus;
    CardView card_view_Indvidual,card_view_company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = getInstance();
       init();
//       Btn_Company();
//       Btn_Indvidual();
       Registeer_Indvidual();

    }

    private void Registeer_Indvidual() {
        Btn_Register=findViewById(R.id.Btn_Register);
        Btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               if(Type.equals("company")){
//                   if (!ValidateFirstNameCompany()||!ValidateLastNameCompany()||!ValidateFullName_Company()||!ValidateName_Company()
//                           ||!ValidatePhone_Company()
//                           ||!ValidateEmail_Company() ||ValidateEPassword_Company()
//                   ){
//                   } else{
//                       username = E_FullNameCompany.getText().toString();
//                       final String  pas = E_PasswordCompany.getText().toString();
//                       final String Emaail = E_EmailCompany.getText().toString();
//                       final String Email = Emaail.replaceAll("\\s","");
//
//                       progressBarRegister.setVisibility(View.VISIBLE);
//                       final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
//
//                       databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
//                           @Override
//                           public void onDataChange(DataSnapshot dataSnapshot) {
//                               if(dataSnapshot.exists()) {
//                                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                       if (dataSnapshot1.exists()) {
//                                           progressBarRegister.setVisibility(View.GONE);
//                                           Toast.makeText(Register.this, "" + getResources().getString(R.string.uniqueusername), Toast.LENGTH_SHORT).show();
//                                       }
//                                   }
//                               }else {
//                                   RegisterCompany(Email,pas);
//                               }
//                           }
//
//                           @Override
//                           public void onCancelled(DatabaseError databaseError) {
//
//                           }
//                       });
//                   }
//               }else {
                   if (!ValidateFirstName()||!ValidateLastName()||!ValidateUsername()
                           ||!ValidatePhone()
                           ||!ValidateEmail()
                           ||!ValidatePassword()
                   ){
                   } else{
                       username = E_FullName.getText().toString();
                       final String  pas = E_Password.getText().toString();
                      final String Emaail = E_Email.getText().toString();
                       final String Email = Emaail.replaceAll("\\s","");

                       progressBarRegister.setVisibility(View.VISIBLE);
                       final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");

                       databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               if(dataSnapshot.exists()) {
                                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                       if (dataSnapshot1.exists()) {
                                           progressBarRegister.setVisibility(View.GONE);
                                           Toast.makeText(Register.this, "" + getResources().getString(R.string.uniqueusername), Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               }else {
                                   RegisterIndvidual(Email,pas);
                               }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });


                   }
//               }


            }
        });

    }

    private void init(){
        Type="indvidual";
        progressBarRegister=findViewById(R.id.progressBarRegister);
//        E_FullNameCompany=findViewById(R.id.E_FullNameCompany);
        E_FirstName = findViewById(R.id.E_FirstName);
        E_LastName = findViewById(R.id.E_LastName);
//        E_FirstNameCompany = findViewById(R.id.E_FirstNameCompany);
//        E_LastNameCompany = findViewById(R.id.E_LastNameCompany);

        E_FullName = findViewById(R.id.E_FullName);
        E_Phone = findViewById(R.id.E_Phone);
//        E_EmailCompany=findViewById(R.id.E_EmailCompany);
//        E_PasswordCompany=findViewById(R.id.E_PasswordCompany);
//        E_Company=findViewById(R.id.E_Company);
//        E_PhoneCompany=findViewById(R.id.E_PhoneCompany);
        E_Email=findViewById(R.id.E_Email);
        E_Password=findViewById(R.id.E_Password);
//        T_Company = findViewById(R.id.T_Company);
//        T_Indvidual = findViewById(R.id.T_Indvidual);
        card_view_Indvidual=findViewById(R.id.card_view_Indvidual);
//        card_view_company=findViewById(R.id.card_view_company);


    }

    private Boolean ValidateYears(){
        if (E_Years.getText().toString().trim().isEmpty()){
            E_Years.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }
    private Boolean ValidateEPassword_Company(){
        if(E_PasswordCompany.getText().toString().trim().isEmpty()){
            E_PasswordCompany.setError(getResources().getString(R.string.enterpas));
            return false;
        }else if (E_PasswordCompany.getText().toString().trim().length()<=6){
            E_PasswordCompany.setError(getResources().getString(R.string.pasmin));
            return false;
        }
        else {
            return true;
        }
    }
    private Boolean ValidateLocation(){
        if (E_Location.getText().toString().trim().isEmpty()){
            E_Location.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }
    private Boolean ValidateUsername(){
        if (E_FullName.getText().toString().trim().isEmpty()){
            E_FullName.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }
    private Boolean ValidatePhone(){
        if (E_Phone.getText().toString().trim().isEmpty()){
            E_Phone.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }
    private Boolean ValidateCompanyName(){
        if (E_CompanyName.getText().toString().trim().isEmpty()){
            E_CompanyName.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }
    private Boolean ValidateEmail(){
        String EMAIL=E_Email.getText().toString().trim();
        if (EMAIL.isEmpty()||!isValidEmail(EMAIL)){
            E_Email.setError(getResources().getString(R.string.invalidemail));

            return false;
        }else if(!E_Email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            E_Email.setError(getResources().getString(R.string.invalidemail));
            return false;
        }
        return true;
    }
    private Boolean ValidatePassword(){
        if(E_Password.getText().toString().trim().isEmpty()){
            E_Password.setError(getResources().getString(R.string.enterpas));
            return false;
        }else if (E_Password.getText().toString().trim().length()<=6){
            E_Password.setError(getResources().getString(R.string.pasmin));
            return false;
        }
        else {
            return true;
        }}
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void Btn_Indvidual() {
        T_Indvidual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type="indvidual";

                T_Company.setTextColor(Color.parseColor("#a2a2a2"));
                T_Indvidual.setTextColor(Color.parseColor("#ffffff"));
                T_Company.setBackgroundColor(Color.parseColor("#ffffff"));
                T_Indvidual.setBackgroundResource(R.drawable.bc_leftselected);
                card_view_Indvidual.setVisibility(View.VISIBLE);
                card_view_company.setVisibility(View.GONE);
            }
        });

    }

    public void Btn_Company() {

        T_Company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type="company";
                T_Indvidual.setTextColor(Color.parseColor("#a2a2a2"));
                T_Company.setTextColor(Color.parseColor("#ffffff"));
                T_Indvidual.setBackgroundColor(Color.parseColor("#ffffff"));
                T_Company.setBackgroundResource(R.drawable.bc_rightselected);
                card_view_Indvidual.setVisibility(View.GONE);
                card_view_company.setVisibility(View.VISIBLE);

            }
        });

    }



    private Boolean ValidatePhone_Company(){
        if (E_PhoneCompany.getText().toString().trim().isEmpty()){
            E_PhoneCompany.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }

    private Boolean ValidateEmail_Company(){
        if (E_EmailCompany.getText().toString().trim().isEmpty()){
            E_EmailCompany.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }

    private Boolean ValidateFirstName(){
        if (E_FirstName.getText().toString().trim().isEmpty()){
            E_FirstName.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }

    private Boolean ValidateLastName(){
        if (E_LastName.getText().toString().trim().isEmpty()){
            E_LastName.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }

    private void RegisterIndvidual(String Emaail,String pas){
        auth.createUserWithEmailAndPassword(Emaail,pas).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarRegister.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    Toast.makeText(Register.this, getResources().getString(R.string.alreadyuser) ,
                            Toast.LENGTH_SHORT).show();
                } else {
                    String token= SharedPrefManager.getInstance(Register.this).getDeviceToken();

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
                    FirebaseUser user = getInstance().getCurrentUser();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("username",E_FullName.getText().toString());
                    hashMap.put("phone",E_Phone.getText().toString());
                    hashMap.put("email",E_Email.getText().toString());
                    hashMap.put("f_name",E_FirstName.getText().toString());
                    hashMap.put("f_last",E_LastName.getText().toString());
                    hashMap.put("type","1");
                    hashMap.put("token",token);
                    hashMap.put("id",user.getUid());
                    databaseReference.push().setValue(hashMap);
                    SharedPrefManager.getInstance(getBaseContext()).saveMyName(E_FullName.getText().toString());
                    SharedPrefManager.getInstance(getBaseContext()).saveSocialId(user.getUid());
//                                      user.sendEmailVerification();
//                                      Toast.makeText(MainActivity.this,
//                                              getResources().getString(R.string.verfiymail) + user.getEmail(),
//                                              Toast.LENGTH_SHORT).show();
                    auth.getInstance().signOut();
                    Intent intent=new Intent(Register.this,Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private Boolean ValidateFirstNameCompany(){
        if (E_FirstNameCompany.getText().toString().trim().isEmpty()){
            E_FirstNameCompany.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }

    private Boolean ValidateLastNameCompany(){
        if (E_LastNameCompany.getText().toString().trim().isEmpty()){
            E_LastNameCompany.setError(getResources().getString(R.string.feildempty));
            return false;
        }
        return true;
    }


    private void RegisterCompany(String Emaail,String pas){
        auth.createUserWithEmailAndPassword(Emaail,pas).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarRegister.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    Toast.makeText(Register.this, getResources().getString(R.string.alreadyuser) ,
                            Toast.LENGTH_SHORT).show();
                } else {
                    String token= SharedPrefManager.getInstance(Register.this).getDeviceToken();

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
                    FirebaseUser user = getInstance().getCurrentUser();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("username",E_FullNameCompany.getText().toString());
                    hashMap.put("c_name",E_Company.getText().toString());
                    hashMap.put("phone",E_PhoneCompany.getText().toString());
                    hashMap.put("email",E_EmailCompany.getText().toString());
                    hashMap.put("f_name",E_FirstNameCompany.getText().toString());
                    hashMap.put("f_last",E_LastNameCompany.getText().toString());
                    hashMap.put("token",token);
                    hashMap.put("type","2");

                    hashMap.put("id",user.getUid());
                    databaseReference.push().setValue(hashMap);
                    SharedPrefManager.getInstance(getBaseContext()).saveMyName(E_FullNameCompany.getText().toString());
                    SharedPrefManager.getInstance(getBaseContext()).saveSocialId(user.getUid());
//                                      user.sendEmailVerification();
//                                      Toast.makeText(MainActivity.this,
//                                              getResources().getString(R.string.verfiymail) + user.getEmail(),
//                                              Toast.LENGTH_SHORT).show();
                    auth.getInstance().signOut();
                    Intent intent=new Intent(Register.this,Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
