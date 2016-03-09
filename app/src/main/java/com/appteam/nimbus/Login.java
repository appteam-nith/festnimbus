package com.appteam.nimbus;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
                startActivity(new Intent(Login.this, Registar.class));
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
                    if(response.getString("status").equals("Login successful!")){
                    startActivity(new Intent(Login.this,homeActivity.class));
                        String token=response.getString("data");
                        personalData.SaveToken(token);
                        personalData.SaveData(true);
                        loadToast.success();

                    finish();}
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
                        Toast.makeText(Login.this,"INVALID PASSWORD OR USERNAME",Toast.LENGTH_SHORT).show();
                    }
                    if(error instanceof TimeoutError){
                        Toast.makeText(Login.this,"TIME OUT ERROR",Toast.LENGTH_SHORT).show();
                    }
                    else if(error instanceof ServerError){
                        Toast.makeText(Login.this,"SERVICE ERROR",Toast.LENGTH_SHORT).show();
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
