package com.bekya.bekyaa.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.bekya.bekyaa.MyVolley;
import com.bekya.bekyaa.R;
import com.bekya.bekyaa.tokenid.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

public class ReportsPopup extends AppCompatActivity {
    ArrayAdapter<String> listReports;
    Spinner spinner_Reports;
    EditText E_Report;
    Button Btn_Confirm;
    String ProductKey,UserName,Report;
    DatabaseReference data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reports_popup);
        init();
        getTypes();
        ConFirm();

    }

    private void ConFirm() {
        Btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!E_Report.getText().toString().isEmpty()){
                    Report=E_Report.getText().toString();
                }
                String tokenadmin= SharedPrefManager.getInstance(ReportsPopup.this).getTokenAdmin();

                SendMessage(tokenadmin,Report);
                data= FirebaseDatabase.getInstance().getReference().child("Reports").push();
                HashMap<String, String> map = new HashMap<>();
                map.put("report", Report);
                map.put("productkey", ProductKey);
                map.put("username", UserName);
                map.put("report_key", data.getKey());
                data.setValue(map);
                Toast.makeText(ReportsPopup.this,getResources().getString(R.string.reportsucces), Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

    public void SendMessage(final String token, final String Msg){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://wasalniegy.com/pushem.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(ProductList.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
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

                params.put("title", getResources().getString(R.string.new_report));


                params.put("message",Msg);
                params.put("email", token);
                return params;
            }
        };

        MyVolley.getInstance(getBaseContext()).addToRequestQueue(stringRequest);

    }

    private void init() {
        ProductKey=getIntent().getStringExtra("id");
        UserName=getIntent().getStringExtra("username");
        spinner_Reports=findViewById(R.id.spinner_Reports);
        E_Report=findViewById(R.id.E_Report);
        Btn_Confirm=findViewById(R.id.Btn_Confirm);
    }


    private void getTypes() {
        listReports = new ArrayAdapter<String>(this, R.layout.spinner_item_selected, getResources().getStringArray(R.array.reports)) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                return textView;
            }
        };
        listReports.setDropDownViewResource(R.layout.customtextcolor);
        spinner_Reports.setAdapter(listReports);
        spinner_Reports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type = spinner_Reports.getSelectedItem().toString();
                if(type.equals("Others")||type.equals("اخرى")){
                    E_Report.setVisibility(View.VISIBLE);
                    E_Report.setText(null);
                }else {
                    Report=type;
                    E_Report.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
