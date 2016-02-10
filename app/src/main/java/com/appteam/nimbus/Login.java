package com.appteam.nimbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private PersonalData personalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        personalData = new PersonalData(this);
        findViewById(R.id.registar_Btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registar.class));
            }
        });
        EditText username = (EditText) findViewById(R.id.user_login);
        EditText password = (EditText) findViewById(R.id.password_registar);
        if (Utils.checkData(username.getText().toString()) && Utils.checkData(password.getText().toString())) {
      findViewById(R.id.Login_Btn_login).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(Login.this,homeActivity.class));
              finish();
              // sendRequest(username.getText().toString(),password.getText().toString());
          }
      });

        }
        else {
            Toast.makeText(this,"PLEASE ENTER THE REQUIRED DATA",Toast.LENGTH_SHORT).show();
        }


    }
    private void sendRequest(String string2,String string1) {
        Map<String,String> params=new HashMap<String, String>();
        params.put("username",string2);
        params.put("password",string1);
        Log.d("json",new JSONObject(params).toString());
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,getURL(), new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response_login",""+response.toString());
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
}
