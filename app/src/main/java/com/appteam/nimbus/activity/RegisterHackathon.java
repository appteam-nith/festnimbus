package com.appteam.nimbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
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
import com.android.volley.toolbox.StringRequest;
import com.appteam.nimbus.R;
import com.appteam.nimbus.app.MyApplication;
import com.appteam.nimbus.helper.Connection;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.singleton.MySingleton;

import net.steamcrafted.loadtoast.LoadToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterHackathon extends AppCompatActivity {
    private LoadToast loadToast;
    private EditText email, skill, projectideas, suggestions,name,rollno,phoneno;
    private TextInputLayout emailTextInputLayout, skillTextInputLayout, projectideasTextInputLayout,nameTextInputLayout,roolnoTextInputLayout,phonenoTextInputLayout;
    private boolean isemail = false, isskill = false, isprojectideas = false,isname=false;
    private boolean isphone=false;
    private boolean isValidRollNo=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hackathon);
        name= (EditText) findViewById(R.id.name_registar_hackathon);
        rollno= (EditText) findViewById(R.id.rollno_registar_hackathon);
        phoneno= (EditText) findViewById(R.id.phoneno_registar_hackathon);
        email = (EditText) findViewById(R.id.email_registar_hackathon);
        skill = (EditText) findViewById(R.id.skill_registar_hackathon);
        projectideas = (EditText) findViewById(R.id.projectideas_edittext);
        suggestions = (EditText) findViewById(R.id.suggestions_edittext);
        emailTextInputLayout = (TextInputLayout) findViewById(R.id.email_registar_hackathon_textinputLayout);
        skillTextInputLayout = (TextInputLayout) findViewById(R.id.skill_registar_hackathon_textinputLayout);
        projectideasTextInputLayout = (TextInputLayout) findViewById(R.id.projectideas_textinputlayout);
        nameTextInputLayout= (TextInputLayout) findViewById(R.id.name_registar_hackathon_textinputLayout);
        roolnoTextInputLayout= (TextInputLayout) findViewById(R.id.rollno_registar_hackathon_textinputLayout);
        phonenoTextInputLayout= (TextInputLayout) findViewById(R.id.phoneno_registar_hackathon_textinputLayout);
        loadToast = new LoadToast(this);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Utils.checkData(name.getText().toString())) {
                    nameTextInputLayout.setErrorEnabled(false);
                    isname = true;
                } else {
                    nameTextInputLayout.setError("PLEASE ENTER THE NAME");
                    isname= false;
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
                if (Utils.checkData(email.getText().toString())&& Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    emailTextInputLayout.setErrorEnabled(false);
                    isemail = true;
                } else {
                    emailTextInputLayout.setError("PLEASE ENTER THE VALID EMAIL");
                    isemail = false;
                }
            }
        });
        skill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                skillTextInputLayout.setErrorEnabled(false);
                if (Utils.checkData(skill.getText().toString())) {
                    isskill = true;
                } else {
                    isskill = false;
                    skillTextInputLayout.setError("Please Enter ur Skills");
                }
            }
        });

        projectideas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Utils.checkData(projectideas.getText().toString())) {
                    projectideasTextInputLayout.setErrorEnabled(false);
                    isprojectideas = true;
                } else {
                    projectideasTextInputLayout.setError("PLEASE ENTER THE PROJECT IDEAS");
                    isprojectideas = false;
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
                if (Utils.checkData(phoneno.getText().toString())&&phoneno.getText().toString().length()==10) {
                    phonenoTextInputLayout.setErrorEnabled(false);
                    isphone = true;
                } else {
                    phonenoTextInputLayout.setError("PLEASE ENTER THE PHONE NUMBER");
                    isphone= false;
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
                    roolnoTextInputLayout.setErrorEnabled(false);
                    isValidRollNo=true;
                }else{
                    roolnoTextInputLayout.setErrorEnabled(true);
                    isValidRollNo=false;
                    roolnoTextInputLayout.setError("Enter Valid RollNo");
                }

            }
        });
        findViewById(R.id.button_register_hackathon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new Connection(RegisterHackathon.this).isInternet()) {
                    if (isskill && isemail && isprojectideas&&isname&&isphone&&isValidRollNo) {
                        loadToast.setText("Loading");
                        loadToast.show();
                        String ideas = projectideas.getText().toString();
                        String suggess = "";
                        if (suggestions.getText() != null) {
                            suggess = suggestions.getText().toString();
                        }

                        sendRegisterRequest(getURL(),name.getText().toString(), skill.getText().toString(), ideas, suggess,email.getText().toString(),phoneno.getText().toString(),rollno.getText().toString());
                    } else {
                        Toast.makeText(RegisterHackathon.this, "PLEASE ENTER THE  REQUIRED DETAIL", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterHackathon.this, "PLEASE CONNECT TO INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRegisterRequest(String url, final String name, final String skill, final String ideas, final String suggess, final String email, final String phoneno, final String rollno) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result=getResponse(response);
                              if(!result.equals("Already Registered !")){
                                    Toast.makeText(RegisterHackathon.this,result,Toast.LENGTH_SHORT).show();
                                       loadToast.success();
                                       startActivity(new Intent(RegisterHackathon.this,homeActivity.class));
                                      finish();
                                   }
                              else {
                                       Toast.makeText(RegisterHackathon.this,result,Toast.LENGTH_LONG).show();
                                      loadToast.error();
                                  }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadToast.error();
                              error.printStackTrace();
                              NetworkResponse networkResponse=error.networkResponse;
                              if(networkResponse!=null){
                                     if(networkResponse.statusCode==401){
                                               Toast.makeText(RegisterHackathon.this,"INVALID PASSWORD OR USERNAME",Toast.LENGTH_SHORT).show();
                                     }
                                      if(error instanceof TimeoutError){
                                               Toast.makeText(RegisterHackathon.this,"TIME OUT ERROR",Toast.LENGTH_SHORT).show();
                                           }
                                       else if(error instanceof ServerError){
                                               Toast.makeText(RegisterHackathon.this,"SERVICE ERROR",Toast.LENGTH_SHORT).show();
                                           }
                                  }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("emailid",email);
                map.put("name", name);
                map.put("rollno",rollno);
                map.put("phoneno",phoneno);
                map.put("languageinterested", skill);
                map.put("projectidea", ideas);
                map.put("suggestions", suggess);
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(stringRequest);
    }
    private  String getResponse(String response ) {
           Document document= Jsoup.parse(response);
           String string=document.getElementsByTag("p").text();
            return string;
        }
    private String getURL() {
        return "http://hackathonnith.herokuapp.com/newentry";
    }
}
