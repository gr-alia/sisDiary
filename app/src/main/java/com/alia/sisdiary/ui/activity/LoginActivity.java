package com.alia.sisdiary.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alia.sisdiary.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_GOOGLE_LOGIN = 9001;

    private SignInButton googleLoginButton;
    private LoginButton fbLoginButton;
    private TextView mErrorView;
    private static GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    private SharedPreferences mSpUserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setupViews();
        googlePrepare();
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, REQUEST_GOOGLE_LOGIN);
            }
        });
        mCallbackManager = CallbackManager.Factory.create();
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userId = loginResult.getAccessToken().getUserId();
                AccessToken accessToken = loginResult.getAccessToken();
                handleFbResult(accessToken);

            }

            @Override
            public void onCancel() {
                mErrorView.setText("Log In was canceled");

            }

            @Override
            public void onError(FacebookException error) {
                mErrorView.setText("Log in error" + String.valueOf(error));
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        //For FB login
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setupViews() {
        googleLoginButton = (SignInButton) findViewById(R.id.google_login_button);
        googleLoginButton.setSize(SignInButton.SIZE_WIDE);
        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        mErrorView = (TextView) findViewById(R.id.error_view);

    }

    private void googlePrepare() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getBaseContext(), "Some error happens", Toast.LENGTH_SHORT);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String userId = acct.getId();
            String photoUrl = String.valueOf(acct.getPhotoUrl());
            String userName = acct.getDisplayName();

            mSpUserData = getSharedPreferences(
                    getString(R.string.pref_user_data), MODE_PRIVATE);
            SharedPreferences.Editor editor = mSpUserData.edit();
            editor.putString(getString(R.string.saved_login_type), getString(R.string.login_type_google));
            editor.putString(getString(R.string.saved_user_id), userId);
            editor.putString(getString(R.string.saved_user_photourl), photoUrl);
            editor.putString(getString(R.string.saved_user_name), userName);
            editor.commit();
            finish();

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleFbResult(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        if (response.getError() == null) {
                            String userId = null;
                            String userName = null;
                            String photoUrl = null;
                            try {
                                userId = object.getString("id");
                                photoUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                userName = object.getString("name");
                                Log.i(TAG, userId + userName + photoUrl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mSpUserData = getSharedPreferences(
                                    getString(R.string.pref_user_data), MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSpUserData.edit();
                            editor.putString(getString(R.string.saved_login_type), getString(R.string.login_type_facebook));
                            editor.putString(getString(R.string.saved_user_id), userId);
                            editor.putString(getString(R.string.saved_user_photourl), photoUrl);
                            editor.putString(getString(R.string.saved_user_name), userName);
                            editor.commit();
                            finish();
                        }
                        else {
                            String errorMessage = response.getError().getErrorMessage();
                            Toast.makeText(getBaseContext(), "Failed because of: " + errorMessage, Toast.LENGTH_SHORT);

                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,name,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onBackPressed() {
        //Move the task containing this activity to the back of the activity stack, so
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

}
