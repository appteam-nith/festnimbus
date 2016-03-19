package com.appteam.nimbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {
PersonalData personalData;
    private LoadToast loadToast;
    boolean isemail=true,ispassword=true,isphone=true,isnitian=false,isValidRollNo=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       personalData=new PersonalData(this);
        loadToast=new LoadToast(this);
        final CheckBox checkBox= (CheckBox) findViewById(R.id.choice_register);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                findViewById(R.id.body_roll_registar).setVisibility(View.VISIBLE);
                isnitian=true;
                // added focus on roll number field
                // findViewById(R.id.rollno_registar).set
                }
                else{
                    findViewById(R.id.body_roll_registar).setVisibility(View.GONE);
                isnitian=false;}
            }
        });

        final EditText email= (EditText) findViewById(R.id.email_registar);
        final EditText password= (EditText) findViewById(R.id.password_registar);
        final EditText confirmPassword= (EditText) findViewById(R.id.confirmPassword_registar);
        final EditText phoneno= (EditText) findViewById(R.id.phoneno_registar);
        final EditText rollno= (EditText) findViewById(R.id.rollno_registar);
        isemail=Utils.checkData(email.getText().toString());
        ispassword=Utils.checkData(password.getText().toString());
        isphone=Utils.checkData(phoneno.getText().toString());
        final TextInputLayout emailTextInputLayout= (TextInputLayout) findViewById(R.id.email_registar_textinputLayout);
        final TextInputLayout passwordTextInputLayout= (TextInputLayout) findViewById(R.id.password_registar_textinputLayout);
        final TextInputLayout confirmTextInputLayout= (TextInputLayout) findViewById(R.id.confirmPassword_registar_textinputLayout);
        final TextInputLayout phonenoTextInputLayout= (TextInputLayout) findViewById(R.id.phone_registar_textinputLayout);
        final TextInputLayout rollnoTextInputLayout= (TextInputLayout) findViewById(R.id.rollno_registar_textinputLayout);

        findViewById(R.id.registar_Btn_registar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((isemail&&ispassword&&isphone&&(isnitian==false)||(isemail&&ispassword&&isphone&&isnitian&&isValidRollNo))){

                    Connection cd = new Connection(getApplicationContext());

                    Boolean isInternetPresent = cd.isInternet();
                    if(isInternetPresent)
                    {
                    loadToast.setText("LOADING");
                    loadToast.show();
                    sendRequest(email.getText().toString(),password.getText().toString(),phoneno.getText().toString(),rollno.getText().toString().toUpperCase().trim(),isnitian);
                }else{
                        Toast.makeText(Register.this,"Internet Connection Not Available!!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Register.this,"ENTER DATA REQUIRED",Toast.LENGTH_SHORT).show();
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Utils.checkData(email.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    emailTextInputLayout.setErrorEnabled(false);
                    isemail = true;
                } else {
                    emailTextInputLayout.setError("PLEASE ENTER THE EMAIL");
                    isemail = false;
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(Utils.checkData(password.getText().toString())&& password.getText().toString().length()>8){
                    passwordTextInputLayout.setErrorEnabled(false);
                }
                else {
                    passwordTextInputLayout.setError("PLEASE ENTER MORE THAN 8 CHARACTER");
                }
            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(Utils.checkData(confirmPassword.getText().toString())&&confirmPassword.getText().toString().equals(password.getText().toString())){
                    confirmTextInputLayout.setErrorEnabled(false);
                    ispassword=true;
                }
                                else {
                    confirmTextInputLayout.setError("PASSWORD DOES NOT MATCH");
                    ispassword=false;
                }
            }
        });

        phoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(phoneno.getText().toString().length()==10){
                phonenoTextInputLayout.setErrorEnabled(false);
                isphone=true;}
                else {
                phonenoTextInputLayout.setError("NOT VALID PHONE NUMBER");
                isphone=false;
                }
            }
        });

        rollno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String input=rollno.getText().toString();
                String ptr="((1(4|5)MI5((0[1-9])|([1-5][0-9])|60))|(1(4|5)M[1-5]((0[1-9])|([1-5][0-9])|60))|(15MI4((0[1-9])|([1-5][0-9])|60))|(1[1-5][1-6]((0[1-9])|([1-8][0-9])|90))|(IIITU1(4|5)(1|2)((0[1-9])|([1-2][0-9])|30)))";

                Pattern p=Pattern.compile(ptr);
                Matcher m=p.matcher(input.toUpperCase().trim());

                if(m.matches()){
                   rollnoTextInputLayout.setErrorEnabled(false);
                    isValidRollNo=true;
                }else{
                    rollnoTextInputLayout.setErrorEnabled(true);
                    isValidRollNo=false;
                    rollnoTextInputLayout.setError("Enter Valid RollNo");
                }

              }
        });

    }


    private void sendRequest(final String string, String string1, final String string2, final String string3, boolean nitian) {
        Map<String,String> params=new HashMap<String, String>();
        params.put("mobile",string2);
        params.put("email",string);
        params.put("password",string1);
        params.put("rollno",string3);
        params.put("nitian",""+isnitian);
        Log.d("json",new JSONObject(params).toString());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,getURL(), new JSONObject(params),
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("qw",""+response.toString());
                try {
                    if(response.has("message")){
                      String message=response.getString("message");
                        Toast.makeText(Register.this,message,Toast.LENGTH_SHORT).show();
                        loadToast.error();
                    }else{
                        if(response.getString("data").equals("Registered Successfully")){
                            loadToast.success();
                            startActivity(new Intent(Register.this, Login.class));
                            finish();}
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadToast.error();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadToast.error();
                NetworkResponse networkResponse=error.networkResponse;
                if(networkResponse!=null){
                    if(networkResponse.statusCode==401){
                        Toast.makeText(Register.this,"INVALID PASSWORD OR USERNAME",Toast.LENGTH_SHORT).show();
                    }
                    if(error instanceof TimeoutError){
                        Toast.makeText(Register.this,"TIME OUT ERROR",Toast.LENGTH_SHORT).show();
                    }
                    else if(error instanceof ServerError){
                        Toast.makeText(Register.this,"SERVICE ERROR",Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                }

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap=new HashMap<>();
                headerMap.put("Content-type","application/json");
                return headerMap;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }


    private String getURL() {
        return "https://festnimbus.herokuapp.com/api/user/register";
    }



}
