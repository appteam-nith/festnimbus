package com.appteam.nimbus;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class Login extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN =12 ;
    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress,mSignInError;
    private final int STATE_SIGNED_IN=0;
    private final int STATE_SIGN_IN=1;
    private final int STATE_PROGRESS=2;
    private PendingIntent mPendingIntent;
    private  PersonalData personalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mGoogleApiClient=buildApiClient();
        personalData=new PersonalData(this);
        findViewById(R.id.SignInBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolveSignInError();
            }
        });
    }

    private void resolveSignInError() {
        if(mPendingIntent!=null){

            try {
                mSignInProgress=STATE_PROGRESS;
                startIntentSenderForResult(mPendingIntent.getIntentSender(),RC_SIGN_IN,null,0,0,0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
                mSignInProgress=STATE_SIGN_IN;
                mGoogleApiClient.connect();

            }
        }
    }

    private GoogleApiClient buildApiClient() {
        return mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle){
        if(mGoogleApiClient.isConnected()&&mGoogleApiClient!=null){
            mSignInProgress=STATE_SIGNED_IN;
            Person currentPerson=Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            if(currentPerson!=null){
                String email=Plus.AccountApi.getAccountName(mGoogleApiClient);
                personalData.SaveData(true,currentPerson.getDisplayName(),email,currentPerson.getImage().getUrl());
              Intent i=new Intent(Login.this,homeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
           mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(mSignInProgress!=STATE_PROGRESS){
            mPendingIntent=connectionResult.getResolution();
            mSignInError=connectionResult.getErrorCode();
            if(mSignInProgress==STATE_SIGN_IN){
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient!=null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient!=null)
            if(mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RC_SIGN_IN:
                if(requestCode==RESULT_OK){
                    mSignInProgress=STATE_SIGN_IN;

                }
                else {
                    mSignInProgress=STATE_SIGNED_IN;
                }
                if(!mGoogleApiClient.isConnecting()){
                    mGoogleApiClient.connect();
                }
                break;
        }
    }
}
