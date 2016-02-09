package com.appteam.nimbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registar extends AppCompatActivity {
PersonalData personalData;
    boolean isemail=true,ispassword=true,isphone=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
       personalData=new PersonalData(this);
      final CheckBox checkBox= (CheckBox) findViewById(R.id.choice_register);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked())
                findViewById(R.id.body_roll_registar).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.body_roll_registar).setVisibility(View.GONE);
            }
        });

        final EditText email= (EditText) findViewById(R.id.email_registar);
        final EditText password= (EditText) findViewById(R.id.password_registar);
        final EditText confirmPassword= (EditText) findViewById(R.id.confirmPassword_registar);
        final EditText phoneno= (EditText) findViewById(R.id.rollno_registar);
        final EditText rollno= (EditText) findViewById(R.id.rollno_registar);

        findViewById(R.id.registar_Btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ispassword&&ispassword&&isemail){
                    sendRequest(email.getText().toString(),password.getText().toString(),phoneno.getText().toString(),rollno.getText().toString());
                }
                else {
                    Toast.makeText(Registar.this,"ENTER DATA REQUIRED",Toast.LENGTH_SHORT).show();
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
                        Toast.makeText(Registar.this,"WRONG EMAIL ADDRESS",Toast.LENGTH_SHORT).show();
                        isemail=false;
                    }
                }
            }
        });
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!confirmPassword.getText().toString().equals(password.getText().toString())){
                    Toast.makeText(Registar.this,"WRONG PASSWORD",Toast.LENGTH_SHORT).show();
                    ispassword=false;
                }
            }
        });
        phoneno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!Patterns.PHONE.matcher(phoneno.getText().toString()).matches()){
                    Toast.makeText(Registar.this,"WRONG PHONE NUMBER",Toast.LENGTH_SHORT).show();
                    isphone=false;
                }
            }
        });
    }
    private void sendRequest(String string, String string1,String string2,String string3) {
        Map<String,String> params=new HashMap<String, String>();
        params.put("phoneno",string2);
        params.put("email",string);
        params.put("password",string1);
        params.put("rollno",string3);
        Log.d("json",new JSONObject(params).toString());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,getURL(), new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("qw",""+response.toString());
                try {
                    String token=response.getString("data");
                    personalData.SaveToken(token);
                    Log.d("token",""+personalData.getToken());
                    startActivity(new Intent(Registar.this,homeActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap=new HashMap<>();
                headerMap.put("Content-type","application/json");
                return headerMap;
            }
        };
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    private String getURL() {
        return "https://edzoo.herokuapp.com/register/";
    }
    private boolean checkData(String string){
        return  !string.isEmpty()&&string.trim().length()!=0;
    }
}
