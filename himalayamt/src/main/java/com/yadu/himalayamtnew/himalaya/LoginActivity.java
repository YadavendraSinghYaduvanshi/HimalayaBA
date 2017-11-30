package com.yadu.himalayamtnew.himalaya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yadu.himalayamtnew.R;
import com.yadu.himalayamtnew.autoupdate.AutoUpdateActivity;
import com.yadu.himalayamtnew.constants.AlertandMessages;
import com.yadu.himalayamtnew.constants.CommonFunctions;
import com.yadu.himalayamtnew.constants.CommonString;
import com.yadu.himalayamtnew.xmlGetterSetter.FailureGetterSetter;
import com.yadu.himalayamtnew.xmlHandler.LoginGetterSetter;
import com.yadu.himalayamtnew.xmlHandler.XMLHandlers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener,
        LocationListener {

    private AutoCompleteTextView museridView;
    private EditText mPasswordView;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private double latitude = 0.0, longitude = 0.0;
    private String username, password, p_username, p_password, error_msg = "";
    int eventType;
    private int versionCode;
    private Context context;
    private boolean isChecked;
    LoginGetterSetter lgs = null;
    String app_ver, version_name;
    static int counter = 1;

    Button museridSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        declaration();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        museridSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        // Create a Folder for Images
        File file = new File(Environment.getExternalStorageDirectory(), "Himalaya_MT_Images");
        if (!file.isDirectory()) {
            file.mkdir();
        }

    }


    private void attemptLogin() {
        // Reset errors.
        museridView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        username = museridView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_enter_username));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid userid address.
        if (TextUtils.isEmpty(username)) {
            museridView.setError(getString(R.string.error_enter_password));
            focusView = museridView;
            cancel = true;
        } /*else if (!isuseridValid(userid)) {
            museridView.setError(getString(R.string.error_invalid_userid));
            focusView = museridView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else if (!isuseridValid(username)) {
            Snackbar.make(museridView, getString(R.string.error_incorrect_username), Snackbar.LENGTH_SHORT).show();
        } else if (!isPasswordValid(password)) {
            Snackbar.make(museridView, getString(R.string.error_incorrect_password), Snackbar.LENGTH_SHORT).show();
        } else {
            if (CommonFunctions.CheckNetAvailability(context)) {
                new AuthenticateTask().execute();

            } else if (password.equals(p_password)) {
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                AlertandMessages.showToastMsg(context, "No Network and offline login");
            } else {
                AlertandMessages.showToastMsg(context, "Incorrect Password");
            }
            // new AuthenticateTask().execute();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class AuthenticateTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setTitle("Login");
            dialog.setMessage("Authenticating....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String resultHttp = "";
                error_msg = "";
                String userauth_xml =
                        "[DATA]"
                                + "[USER_DATA]"
                                + "[USER_ID]" + username + "[/USER_ID]"
                                + "[Password]" + password + "[/Password]"
                                + "[IN_TIME]" + CommonFunctions.getCurrentTimeHHMMSS() + "[/IN_TIME]"
                                + "[LATITUDE]" + latitude + "[/LATITUDE]"
                                + "[LONGITUDE]" + longitude + "[/LONGITUDE]"
                                + "[APP_VERSION]" + app_ver + "[/APP_VERSION]"
                                + "[ATT_MODE]OnLine[/ATT_MODE]"
                                + "[/USER_DATA]"
                                + "[/DATA]";

                SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_LOGIN);
                request.addProperty("onXML", userauth_xml);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL);
                androidHttpTransport.call(CommonString.SOAP_ACTION_LOGIN, envelope);

                Object result = (Object) envelope.getResponse();

                if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    error_msg = CommonString.MESSAGE_FAILURE;
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                    error_msg = CommonString.MESSAGE_FALSE;
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_CHANGED)) {
                    error_msg = CommonString.MESSAGE_CHANGED;
                } else {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(result.toString()));
                    xpp.next();
                    eventType = xpp.getEventType();
                    FailureGetterSetter failureGetterSetter = XMLHandlers.failureXMLHandler(xpp, eventType);

                    if (failureGetterSetter.getStatus().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                        error_msg = CommonString.METHOD_LOGIN + failureGetterSetter.getErrorMsg();
                    } else {
                        try {
                            // For String source
                            xpp.setInput(new StringReader(result.toString()));
                            xpp.next();
                            eventType = xpp.getEventType();
                            lgs = XMLHandlers.loginXMLHandler(xpp, eventType);

                        } catch (XmlPullParserException e) {
                            error_msg = CommonString.MESSAGE_EXCEPTION;
                            e.printStackTrace();
                        } catch (IOException e) {
                            error_msg = CommonString.MESSAGE_SOCKETEXCEPTION;
                            e.printStackTrace();
                        }

                        // PUT IN PREFERENCES
                        editor.putString(CommonString.KEY_USERNAME, username);
                        editor.putString(CommonString.KEY_PASSWORD, password);
                        editor.putString(CommonString.KEY_VERSION, lgs.getVERSION());
                        editor.putString(CommonString.KEY_PATH, lgs.getPATH());
                        //editor.putString(CommonString.KEY_DATE, "04/05/2017");
                        editor.putString(CommonString.KEY_DATE, lgs.getDATE());
                        editor.putString(CommonString.KEY_USER_TYPE, lgs.getRIGHTNAME());

                        editor.commit();
                        return CommonString.KEY_SUCCESS;
                    }

                    return resultHttp;
                }

                //  return "";

            } catch (MalformedURLException e) {
                error_msg = CommonString.MESSAGE_EXCEPTION;
                e.printStackTrace();

            } catch (IOException e) {
                counter++;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (counter < 3) {
                            dialog.dismiss();
                            new AuthenticateTask().execute();
                        } else {
                            error_msg = CommonString.MESSAGE_SOCKETEXCEPTION;
                            counter = 1;
                        }
                    }
                });


            } catch (Exception e) {
                error_msg = CommonString.MESSAGE_EXCEPTION;
                e.printStackTrace();

            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals(CommonString.KEY_SUCCESS)) {
                if (lgs.getVERSION().equals(Integer.toString(versionCode))) {
                    Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getBaseContext(), AutoUpdateActivity.class);
                    intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                    startActivity(intent);
                    finish();
                }
            } else {
                AlertandMessages.showAlert((Activity) context, "Error in login :" + error_msg, false);
            }
        }
    }


    private boolean isuseridValid(String userid) {
        //TODO: Replace this with your own logic
        boolean flag = true;
        String u_id = preferences.getString(CommonString.KEY_USERNAME, "");
        if (!u_id.equals("") && !userid.equalsIgnoreCase(u_id)) {
            flag = false;
        }
        return flag;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        boolean flag = true;
        String pw = preferences.getString(CommonString.KEY_PASSWORD, "");
        if (!pw.equals("") && !password.equals(pw)) {
            flag = false;
        }
        return flag;
    }

    void declaration() {
        context = this;
        TextView tv_version = (TextView) findViewById(R.id.tv_version_code);
        tv_version.setText("Version - " + app_ver);
        // Set up the login form.
        museridView = (AutoCompleteTextView) findViewById(R.id.userid);
        mPasswordView = (EditText) findViewById(R.id.password);
        museridSignInButton = (Button) findViewById(R.id.user_login_button);
        // museridView.setText("testmer");
        //mPasswordView.setText("cpm123");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        p_username = preferences.getString(CommonString.KEY_USERNAME, null);
        p_password = preferences.getString(CommonString.KEY_PASSWORD, null);
        isChecked = preferences.getBoolean(CommonString.KEY_REMEMBER, false);
        editor = preferences.edit();
    }

}

