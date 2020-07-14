package com.technoplanet.p360;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.technoplanet.p360.Async.ServiceAsync;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFname;
    private EditText edtLname;
    private EditText edtUname;
    private EditText edtEmail;
    private EditText edtPW;
    private Switch svU;
    private Switch svP;
    private ImageView dtB;
    private EditText edtphN;
    private Button btnCont;
    private EditText edtDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtFname = findViewById(R.id.edtFname);
        edtLname = findViewById(R.id.edtLname);
        edtUname = findViewById(R.id.edtUname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPW = findViewById(R.id.edtPW);
        svU = findViewById(R.id.svU);
        svP = findViewById(R.id.svP);
        dtB = findViewById(R.id.dtB);
        edtDOB = findViewById(R.id.edtDOB);
        edtphN = findViewById(R.id.edtphN);
        btnCont = findViewById(R.id.btnCont);

        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBlank() == true) {
                    if (edtPW.length() <= 6) {
                        edtPW.setError("Please enter minimum 6 characters");
                    } else {
                        serviceCallForRegister();
                    }
                }

                Log.e("Fname", edtFname.getText().toString() +
                        "\n Lname " + edtLname.getText().toString() +
                        "\n Uname " + edtUname.getText().toString() +
                        "\n Email " + edtEmail.getText().toString() +
                        "\n PW" + edtPW.getText().toString() +
                        "\n DOB" + edtDOB.getText().toString() +
                        "\n PHn" + edtphN.getText().toString());
            }
        });

        dtB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void serviceCallForRegister() {
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "http://training2.ncryptedprojects.com/ws/sweta_sep18/web-services/register.php";
        //?firstName=abc&lastName=def&userName=abcdefghi&email=abc@gmail.com&password=12345

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("firstName", edtFname.getText().toString().trim());
        builder.appendQueryParameter("lastName", edtLname.getText().toString().trim());
        builder.appendQueryParameter("userName", edtUname.getText().toString().trim());
        builder.appendQueryParameter("email", edtEmail.getText().toString().trim());
        builder.appendQueryParameter("password", edtPW.getText().toString().trim());

        new ServiceAsync(url, new ServiceAsync.OnAsyncResult() {
            @Override
            public void onSuccess(String result) {
                progressDialog.cancel();

                try {
                    JSONObject obj1 = new JSONObject(result);
                    String strStatus = obj1.getString("status");
                    String strStatusCode = obj1.getString("statusCode");

                    if (strStatus.equals("valid") && strStatusCode.equals("1")) {

                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please register..", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String result) {
                progressDialog.cancel();
                Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();

            }
        }, builder).execute();

    }

    private void showDatePicker() {

        Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtDOB.setText(dayOfMonth + "/" + month + "/" + year);
                edtDOB.setError("");
            }
        }, y, m, d);

        datePickerDialog.show();

    }

    private boolean isBlank() {

        String strFirstname = edtFname.getText().toString().trim();
        String strLastname = edtLname.getText().toString().trim();
        String strUsername = edtUname.getText().toString().trim();
        String strEmailid = edtEmail.getText().toString().trim();
        String strPassword = edtPW.getText().toString().trim();
        String strDOB = edtDOB.getText().toString().trim();
        String strPhNo = edtphN.getText().toString().trim();

        if (strFirstname.equals("")) {
            edtFname.setError("Please enter ur FirstName");
            return false;
        } else if (strLastname.equals("")) {
            edtLname.setError("Please enter ur LastName");
            return false;
        } else if (strUsername.equals("")) {
            edtUname.setError("Please enter ur UserName");
            return false;
        } else if (strEmailid.equals("")) {
            edtEmail.setError("Please enter ur Emailid");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError("Please enter valid email id");
            return false;
        } else if (strPassword.equals("")) {
            edtPW.setError("Please enter ur Password");
            return false;
        } else if (strDOB.equals("")) {
            edtDOB.setError("Please enter ur Date of Birth");
            return false;
        } else if (strPhNo.equals("")) {
            edtphN.setError("Please enter ur Phone No.");
            return false;
        } else {
            return true;
        }
    }
}
