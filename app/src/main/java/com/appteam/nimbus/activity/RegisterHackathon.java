package com.appteam.nimbus.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.appteam.nimbus.helper.Connection;
import com.appteam.nimbus.singleton.MySingleton;
import com.appteam.nimbus.model.PersonalData;
import com.appteam.nimbus.R;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.app.MyApplication;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterHackathon extends AppCompatActivity {
private String pref1,pref2,pref3;
    private LoadToast loadToast;
    private EditText email,skill,projectideas,suggestions;
    private TextInputLayout emailTextInputLayout,skillTextInputLayout,projectideasTextInputLayout,suggestionsTextInputLayout;
    private boolean isemail=false,isskill=false,ispref1=true,ispref2=true,ispref3=true,isprojectideas=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hackathon);
        Spinner preference1= (Spinner) findViewById(R.id.spinner_preference1);
        Spinner preference2= (Spinner) findViewById(R.id.spinner_preference2);
        Spinner preference3= (Spinner) findViewById(R.id.spinner_preference3);
        email= (EditText) findViewById(R.id.email_registar_hackathon);
        skill= (EditText) findViewById(R.id.skill_registar_hackathon);
        projectideas= (EditText) findViewById(R.id.projectideas_edittext);
        suggestions= (EditText) findViewById(R.id.suggestions_edittext);
        emailTextInputLayout= (TextInputLayout) findViewById(R.id.email_registar_hackathon_textinputLayout);
        skillTextInputLayout= (TextInputLayout) findViewById(R.id.skill_registar_hackathon_textinputLayout);
        projectideasTextInputLayout= (TextInputLayout) findViewById(R.id.projectideas_textinputlayout);
        suggestionsTextInputLayout= (TextInputLayout) findViewById(R.id.suggestions_textinputlayout);
        loadToast=new LoadToast(this);
        final String mentor_first_name[]={"First Preference As Mentor","Akarshit Wal","Pradyot","Shubham Choudhary","Himanshu Singh","Sagar Karira","Prateek Prasher","Akshendra Pratap","Prikshit Tekta","Mayank Bansal","Kunal Sharma","Shubham Rana"};
        final String mentor_second_name[]={"Second Preference As Mentor","Akarshit Wal","Pradyot","Shubham Choudhary","Himanshu Singh","Sagar Karira","Prateek Prasher","Akshendra Pratap","Prikshit Tekta","Mayank Bansal","Kunal Sharma","Shubham Rana"};
        final String mentor_third_name[]={"Third Preference As Mentor","Akarshit Wal","Pradyot","Shubham Choudhary","Himanshu Singh","Sagar Karira","Prateek Prasher","Akshendra Pratap","Prikshit Tekta","Mayank Bansal","Kunal Sharma","Shubham Rana"};
        ArrayAdapter<String> adapter_first=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mentor_first_name);
        adapter_first.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_second=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mentor_second_name);
        adapter_second.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_third=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mentor_third_name);
        adapter_third.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preference1.setAdapter(adapter_first);
        preference2.setAdapter(adapter_second);
        preference3.setAdapter(adapter_third);
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


        preference1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pref1=mentor_first_name[i];
                if(pref1!=null&&pref2!=null&&pref3!=null){
                    if(!(pref1.equals("First Preference As Mentor")&&pref2.equals("Second Preference As mentor")&&pref3.equals("Third Preference As Mentor")))
                    if(pref1.equals("First Preference As Mentor")||pref1.equals(pref2)||pref1.equals(pref3)){
                        Toast.makeText(RegisterHackathon.this,"PLEASE SELECT ANY OTHER MENTOR AS FIRST PREFERENCE ",Toast.LENGTH_SHORT).show();
                        ispref1=false;
                    }
                    else {
                        ispref1=true;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
pref1="";
            }
        });
        preference2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pref2=mentor_second_name[i];
                if(pref1!=null&&pref2!=null&&pref3!=null){
                    if(!(pref1.equals("First Preference As Mentor")&&pref2.equals("Second Preference As mentor")&&pref3.equals("Third Preference As Mentor")))
                    if(pref2.equals("Second Preference As Mentor")||pref2.equals(pref1)||pref2.equals(pref3)){
                        Toast.makeText(RegisterHackathon.this,"PLEASE SELECT ANY OTHER MENTOR AS SECOND PREFERENCE",Toast.LENGTH_SHORT).show();
                        ispref2=false;
                    }
                    else {
                        ispref2=true;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
pref2="";
            }
        });
preference3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        pref3=mentor_third_name[i];
        if(pref1!=null&&pref2!=null&&pref3!=null){
            if(!(pref1.equals("First Preference As Mentor")&&pref2.equals("Second Preference As Mentor")&&pref3.equals("Third Preference As Mentor")))
            if(pref3.equals("Third Preference As Mentor")||pref3.equals(pref2)||pref3.equals(pref1)){
                Toast.makeText(RegisterHackathon.this,"PLEASE SELECT ANY OTHER MENTOR AS THIRD PREFERENCE",Toast.LENGTH_SHORT).show();
                ispref3=false;
            }
            else {
                ispref3=true;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
pref3="";
    }
});
         findViewById(R.id.button_register_hackathon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new Connection(RegisterHackathon.this).isInternet()){
                    if(isskill&&isemail&&ispref1&&ispref2&&ispref3&&isprojectideas){
                        loadToast.setText("Loading");
                        loadToast.show();
                        String ideas=projectideas.getText().toString();
                        String suggess="";
                        if(suggestions.getText()!=null){
                            suggess=suggestions.getText().toString();
                        }

                        sendRegisterRequest(getURL(),email.getText().toString(),skill.getText().toString(),ideas,suggess,pref1,pref2,pref3);
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
    private  void sendRegisterRequest(String url,String name,String skill,String ideas,String suggess,String pref1,String pref2,String pref3){
        PersonalData personalData=new PersonalData(RegisterHackathon.this);
        Map<String,String> params=new HashMap<String, String>();
        params.put("name",name);
        params.put("skill",skill);
        params.put("email",personalData.getEMAIL());
        params.put("rollno",personalData.getROLLNO());
        params.put("phoneno",personalData.getPHONENO());
        params.put("projectideas",ideas);
        params.put("suggesstions",suggess);
        params.put("preference1",pref1);
        params.put("preference2",pref2);
        params.put("preference3",pref3);
        Log.d("json",new JSONObject(params).toString());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadToast.success();
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
