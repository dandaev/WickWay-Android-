package com.example.wikway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.wikway1.R;
import com.example.wikway.ui.AboutUsActivity;
import com.example.wikway.ui.home.HomeFragment;
import com.example.wikway.ui.saved.FavoriteDbHelper;
import com.example.wikway.ui.saved.SavedFragment;
import com.example.wikway.ui.search.SearchFragment;
import com.example.wikway.utils.NoInternet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            checkInternet();
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            bottomNav.setSelectedItemId(R.id.navigation_home);
            App.favoriteDbHelper = new FavoriteDbHelper(this);


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.aboutus:
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment selectedFragment = null;
                        switch (menuItem.getItemId()){
                            case R.id.navigation_home :
                                selectedFragment = HomeFragment.getInstance();
                                break;
                            case R.id.navigation_saved :
                                selectedFragment = SavedFragment.getInstance();
                                break;
                            case R.id.navigation_search :
                                selectedFragment = new SearchFragment();
                                break;

                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,selectedFragment).commit();
                        return true;
                    }
                };

        private void checkInternet(){
            if (!App.isOnline()){
                startActivity(new Intent(MainActivity.this, NoInternet.class));
            }
        }
    }

