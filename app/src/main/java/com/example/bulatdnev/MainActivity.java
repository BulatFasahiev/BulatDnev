package com.example.bulatdnev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bulatdnev.ui.home.HomeFragment;
import com.example.bulatdnev.ui.mark.MarkFragment;
import com.example.bulatdnev.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bNav=findViewById(R.id.bottomNavigationView);
        bNav.setOnItemSelectedListener(bottomNavMet);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new HomeFragment()).commit();
    }


    private NavigationBarView.OnItemSelectedListener bottomNavMet = new NavigationBarView.OnItemSelectedListener() {
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
//                    sTitle.setText("Главная");
                    break;
                case R.id.navigation_mark:
                    fragment = new MarkFragment();
//                    sTitle.setText("Оценки");
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
//                    sTitle.setText("Профиль");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment).commit();

            return true;
        }
    };
}