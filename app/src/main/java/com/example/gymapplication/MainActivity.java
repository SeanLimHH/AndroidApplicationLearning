package com.example.gymapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gymapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainActivityBinding;
    Home home = new Home();
    Profile profile = new Profile();
    Settings settings = new Settings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainActivityBinding.getRoot();
        setContentView(view);

        replaceFragment(home);
        mainActivityBinding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Log.d("MyApp", String.valueOf(item.getItemId()));
            if (item.getItemId() == R.id.homeMenuItem) {
                replaceFragment(home);
            }
            if (item.getItemId() == R.id.profileMenuItem) {
                replaceFragment(profile);

            }
            if (item.getItemId() == R.id.settingsMenuItem) {
                replaceFragment(settings);

            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragmentToReplaceWith) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragmentToReplaceWith).commit();
    }

}