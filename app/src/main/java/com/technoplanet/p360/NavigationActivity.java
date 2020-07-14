package com.technoplanet.p360;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.technoplanet.p360.FragmentUtils.HealthLibFrag;
import com.technoplanet.p360.FragmentUtils.HomeFrag;
import com.technoplanet.p360.FragmentUtils.MyAccFrag;
import com.technoplanet.p360.FragmentUtils.PillRemFrag;

public class NavigationActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView nav;
    private Toolbar toolbar;
   // private MenuView.ItemView optnLogout;
    private SharedPreferences prefs;
    private SharedPreferences.Editor e;
   // private Activity Context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //context=getActivity();
        drawer = findViewById(R.id.drawer);
        nav = findViewById(R.id.nav);

       // optnLogout = findViewById(R.id.optnLogout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences("LoginState",MODE_PRIVATE);
        Boolean b = prefs.getBoolean("loginkey",false);
        e = prefs.edit();

        //to show hamburgen icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(NavigationActivity.this,
                drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);//to show icon
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.optnAcc) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.frm, new MyAccFrag()).commit();

                }
                else if (item.getItemId() == R.id.optnOrdr){
                    Toast.makeText(NavigationActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                }
                else if (item.getItemId() == R.id.optnHome){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frm, new HomeFrag()).commit();
                }
                else if (item.getItemId() == R.id.optnUpload){
                    //Toast.makeText(NavigationActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(NavigationActivity.this,CameraActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (item.getItemId() == R.id.optnLocation){
                   // getSupportFragmentManager().beginTransaction().replace(R.id.frm, new HealthLibFrag()).commit();
                    Intent i = new Intent(NavigationActivity.this,GmapActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (item.getItemId() == R.id.optnPillRem){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frm, new PillRemFrag()).commit();
                }
                else if (item.getItemId() == R.id.optnHlib){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frm, new HealthLibFrag()).commit();
                }
                else if (item.getItemId() == R.id.optnLogout){

                    e.putBoolean("loginkey",false);
                    e.commit();

                    Intent i = new Intent(NavigationActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frm,new HomeFrag()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.optnSearch ){
            Intent i = new Intent(NavigationActivity.this, SearchActivity.class);
            startActivity(i);
            finish();
        }else if (item.getItemId() == R.id.optnAddCart){
            Intent i = new Intent(NavigationActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menusearch, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
