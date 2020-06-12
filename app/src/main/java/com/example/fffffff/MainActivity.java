package com.example.fffffff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fffffff.HOME.Home_fragment;
import com.example.fffffff.LOGIN.LoginActivity;
import com.example.fffffff.MEDIA_PLAYER.media_player_fragment;
import com.example.fffffff.MESSAGEBOARD.Message_fragment;
import com.example.fffffff.PROFILE.Profile_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private AppBarConfiguration mAppBarConfiguration;
    public Toolbar toolbar;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private NavigationView.OnNavigationItemSelectedListener itemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setChecked(true);
            // Set action bar title
            setTitle(item.getTitle());
            switch (item.getItemId()) {
                case R.id.nav_Home:
                    SpannableString spanString = new SpannableString(item.getTitle().toString());
                    spanString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spanString.length(), 0); // fix the color to white
                    item.setTitle(spanString);
                    fragmentManager.beginTransaction().replace(R.id.nav_fragment, new Home_fragment(), null).commit();
                    break;
                case R.id.nav_Profile:

                    fragmentManager.beginTransaction().replace(R.id.nav_fragment, new Profile_Fragment(), null).commit();
                    break;
                case R.id.nav_Media_Player:
                    fragmentManager.beginTransaction().replace(R.id.nav_fragment, new media_player_fragment(MainActivity.this), null).commit();
                    break;
                case R.id.nav_messageboard:
                    fragmentManager.beginTransaction().replace(R.id.nav_fragment, new Message_fragment(MainActivity.this), null).commit();
                    break;
                case R.id.nav_setting:
                    Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_support:
                    Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_LogOut:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
            }

            // Close the navigation drawer
            drawer.closeDrawers();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawer = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(itemSelectedListener);
        navigationView.setItemIconTintList(null);
        View view = navigationView.getHeaderView(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_fragment, new Home_fragment(), null).commit();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_Home, R.id.nav_Profile, R.id.nav_Media_Player, R.id.nav_messageboard, R.id.nav_setting, R.id.nav_support, R.id.nav_LogOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
