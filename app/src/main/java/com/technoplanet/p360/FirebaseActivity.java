package com.technoplanet.p360;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class FirebaseActivity extends AppCompatActivity {
    List<AuthUI.IdpConfig> providers;
    private static final int  My_REQUEST_CODE = 777;
    private SharedPreferences prefs;
    private SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        //Init provider
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),//Email Builder
                new AuthUI.IdpConfig.PhoneBuilder().build(),//Phone Builder
                new AuthUI.IdpConfig.GoogleBuilder().build(),//Google Builder
                new AuthUI.IdpConfig.FacebookBuilder().build()//FB Builder
                //  new AuthUI.IdpConfig.TwitterBuilder().build()//Twitter Builder
        );
        showSignInOptions();
        Log.d("Tag","FIREBASE"+ AuthUI.IdpConfig.FacebookBuilder.class);
    }

    private void showSignInOptions() {
        prefs = getSharedPreferences("LoginState",MODE_PRIVATE);
        Boolean b = prefs.getBoolean("loginkey",false);

        if (b){
            Intent intent = new Intent(FirebaseActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            // AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTheme()
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTheme(R.style.MYTheme)
                            .build(),My_REQUEST_CODE
            );
        }
        e = prefs.edit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == My_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK)
            {
                //Get User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Show email
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                //Set Btn Logout
                e.putBoolean("loginkey",true);
                e.commit();
                Intent intent = new Intent(FirebaseActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                showSignInOptions();
                // serviceCallForLogin();
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("email","error"+response);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.optnSearch){

        }else if (item.getItemId() == R.id.optnAddCart){

        }else if (item.getItemId() == R.id.optnLogout){

        }
        return super.onOptionsItemSelected(item);
    }
}
