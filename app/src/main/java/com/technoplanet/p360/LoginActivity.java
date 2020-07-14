package com.technoplanet.p360;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.technoplanet.p360.Async.ServiceAsync;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin1;
    private EditText edt1, edt2;
    private Button btnFusr;
    private Button btnFpw;
    private Button btnReg1;
    private SharedPreferences prefs;
    private SharedPreferences.Editor e;
    private Context Rcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        btnLogin1 = (Button) findViewById(R.id.btnLogin1);
        btnReg1 = (Button)findViewById(R.id.btnReg1);
        btnFusr = (Button)findViewById(R.id.btnFusr);
        btnFpw = (Button)findViewById(R.id.btnFpw);
        Rcontext = LoginActivity.this;
        manualPermission();

        prefs = getSharedPreferences("LoginState",MODE_PRIVATE);
        Boolean b = prefs.getBoolean("loginkey",false);


        //chk shared pref/login state
        if (b){
            Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(i);
            finish();
        }

        e = prefs.edit();

        btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean c = isBlank();

                if (c){

                    if (edt2.length() <= 6 ){

                        edt2.setError("Please enter atleast 6 characters");

                        e.putBoolean("loginkey",true);
                        e.commit();

                        Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        serviceCallForLogin();
                    }

                }
            }
        });

        btnReg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                //finish wont come as if ur an existing usr den ull return to login again
            }
        });

    }

    public void manualPermission(){
        if (ActivityCompat.checkSelfPermission(Rcontext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            String[] strArray = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS};
            ActivityCompat.requestPermissions(LoginActivity.this, strArray,111);
        } else {
            Toast.makeText(Rcontext, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111 ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] ==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(Rcontext, "Access Granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Rcontext, "Access Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void serviceCallForLogin() {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "http://training2.ncryptedprojects.com/ws/sweta_sep18/web-services/login.php";
        //?email=abc@gmail.com?password=12345

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("email", edt1.getText().toString().trim());
        builder.appendQueryParameter("password", edt2.getText().toString().trim());

        new ServiceAsync(url, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                progressDialog.cancel();

                try {
                    JSONObject obj1 = new JSONObject(result);
                    String strStatus = obj1.getString("status");
                    String strStatusCode = obj1.getString("statusCode");

                    if ( strStatus.equals("valid") && strStatusCode.equals("1") ) {

                        e.putBoolean("loginkey",true);
                        e.putString("Uid",obj1.getString("uId"));//read uId from json
                        e.putString("Username", obj1.getString("userName"));//
                        e.commit();

                        Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(i);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String result) {
                progressDialog.cancel();
                Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();

            }
        },builder).execute();
    }

    private boolean isBlank() {

        String strUsername = edt1.getText().toString().trim();
        String strPassword = edt2.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(edt1.getText().toString()).matches()) {
            edt1.setError("Please enter Username");
            return false;
        } else if (strPassword.equals("")) {
            edt2.setError("Please Enter Password");
            return false;
        } else {
            return true;
        }

    }

}
