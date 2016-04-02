package com.appteam.nimbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.appteam.nimbus.R;
import com.appteam.nimbus.app.MyApplication;
import com.appteam.nimbus.helper.Connection;
import com.appteam.nimbus.helper.Utils;
import com.appteam.nimbus.model.PersonalData;
import com.appteam.nimbus.singleton.MySingleton;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private PersonalData personalData;
    private LoadToast loadToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        personalData = new PersonalData(this);
        loadToast=new LoadToast(this);

        findViewById(R.id.registar_Btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        final EditText username = (EditText) findViewById(R.id.user_login);
        final EditText password = (EditText) findViewById(R.id.password_login);


        findViewById(R.id.Login_Btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.checkData(username.getText().toString()) && Utils.checkData(password.getText().toString())) {
                    Connection cd = new Connection(getApplicationContext());

                    Boolean isInternetPresent = cd.isInternet();
                    if(isInternetPresent)
                    {
                    sendRequest(username.getText().toString(), password.getText().toString());
                    loadToast.setText("LOADING");
                    loadToast.show();}
                    else
                    {
                        Toast.makeText(Login.this,"Internet Connection Not Available!!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Login.this, "PLEASE ENTER THE REQUIRED DATA", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void sendRequest(String string2,String string1) {
        Map<String,String> params=new HashMap<String, String>();
        params.put("email",string2);
        params.put("password", string1);
        Log.d("json", new JSONObject(params).toString());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,getURL(), new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response_login",""+response.toString());
                try {
                    if(response.has("status")){
                        if(response.getString("status").equals("Successfully Login!")){
                            startActivity(new Intent(Login.this,homeActivity.class));
                            String token=response.getString("data");
                            personalData.SaveToken(token);
                            personalData.SaveData(true);
                            loadToast.success();
                            finish();}
                    }
                    else if(response.has("message")){
                        loadToast.error();
                        Toast.makeText(Login.this,""+response.getString("message"),Toast.LENGTH_LONG).show();
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
                String responseBody="";
                try {
                    responseBody=new String(networkResponse.data,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Log.v("body",responseBody);

                JSONObject error_jsonObject=new JSONObject();

                if(networkResponse!=null) {
                    if (networkResponse.statusCode == 404) {
                        try {
                            error_jsonObject = new JSONObject(responseBody);
                            String data = "";
                            if (error_jsonObject.has("data")) {
                                data = error_jsonObject.get("data").toString();
                            }
                            if (data.equals("unable_to_login")) {
                                if (error_jsonObject.has("status")) {
                                    Toast.makeText(Login.this, error_jsonObject.get("status").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Login.this, "Service Error", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "Service Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(Login.this, "Time Out Error", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(Login.this, "Service Error", Toast.LENGTH_LONG).show();
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private String getURL() {
        return "https://festnimbus.herokuapp.com/auth/local";
    }
}
