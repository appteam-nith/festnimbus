package com.appteam.nimbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appteam.nimbus.R;
import com.appteam.nimbus.app.MyApplication;
import com.appteam.nimbus.helper.Connection;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.model.PersonalData;
import com.appteam.nimbus.singleton.MySingleton;

import net.steamcrafted.loadtoast.LoadToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class RegisterHackathon extends AppCompatActivity {
    private LoadToast loadToast;
    private EditText email,skill,projectideas,suggestions;
    private TextInputLayout emailTextInputLayout,skillTextInputLayout,projectideasTextInputLayout,suggestionsTextInputLayout;
    private boolean isemail=false,isskill=false,isprojectideas=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hackathon);
        email= (EditText) findViewById(R.id.email_registar_hackathon);
        skill= (EditText) findViewById(R.id.skill_registar_hackathon);
        projectideas= (EditText) findViewById(R.id.projectideas_edittext);
        suggestions= (EditText) findViewById(R.id.suggestions_edittext);
        emailTextInputLayout= (TextInputLayout) findViewById(R.id.email_registar_hackathon_textinputLayout);
        skillTextInputLayout= (TextInputLayout) findViewById(R.id.skill_registar_hackathon_textinputLayout);
        projectideasTextInputLayout= (TextInputLayout) findViewById(R.id.projectideas_textinputlayout);
        suggestionsTextInputLayout= (TextInputLayout) findViewById(R.id.suggestions_textinputlayout);
        loadToast=new LoadToast(this);
        loadToast.setTranslationY((int) Utils.convertDpToPixel(70, RegisterHackathon.this));
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Utils.checkData(email.getText().toString())) {
                    emailTextInputLayout.setErrorEnabled(false);
                    isemail = true;
                } else {
                    emailTextInputLayout.setError("PLEASE ENTER THE NAME");
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
                if(Utils.checkData(skill.getText().toString())){
                isskill=true;}
                else {
                isskill=false;
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
                    isprojectideas=true;
                } else {
                    projectideasTextInputLayout.setError("PLEASE ENTER FIELD");
                    isprojectideas = false;
                }
            }
        });

        suggestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

         findViewById(R.id.button_register_hackathon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new Connection(RegisterHackathon.this).isInternet()){
                    if(isskill&&isemail&&isprojectideas){
                        loadToast.setText("Loading");
                        loadToast.show();
                        String ideas=projectideas.getText().toString();
                        String suggess="";
                        if(suggestions.getText()!=null){
                            suggess=suggestions.getText().toString();
                        }

                        sendRegisterRequest(getURL(),email.getText().toString(),skill.getText().toString(),ideas,suggess);
                    }
                    else {
                        Toast.makeText(RegisterHackathon.this,"PLEASE ENTER THE  REQUIRED DETAIL",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterHackathon.this,"PLEASE CONNECT TO INTERNET",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void sendRegisterRequest(String url, final String name, final String skill, final String ideas, final String suggess){
        final PersonalData personalData=new PersonalData(RegisterHackathon.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result=getResponse(response);
                if(!result.equals("Already Registered !")){
                    Toast.makeText(RegisterHackathon.this,getResponse(response),Toast.LENGTH_LONG).show();
                    loadToast.success();
                    startActivity(new Intent(RegisterHackathon.this,homeActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(RegisterHackathon.this,getResponse(response),Toast.LENGTH_LONG).show();
                    loadToast.error();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                loadToast.error();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("emailid",personalData.getEMAIL());
                map.put("name",name);
                map.put("rollno",personalData.getROLLNO());
                map.put("phoneno",personalData.getPHONENO());
                map.put("languageinterested",skill);
                map.put("projectidea",ideas);
                map.put("suggestions",suggess);
                return map;
            }
        };
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(stringRequest);
    }
    private String getURL() {
        return "http://hackathonnith.herokuapp.com/newentry";
    }
private  String getResponse(String response ) {
    Document document= Jsoup.parse(response);
    String string=document.getElementsByTag("p").text();
    Log.d("as",string);
    return string;
}}