package com.example.cleanarchitecture.task.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cleanarchitecture.R;
import com.example.cleanarchitecture.task.DefaultActivity;
import com.example.cleanarchitecture.task.search.SearchActivity;
import com.google.android.material.navigation.NavigationView;

/**
 * Created by Hwang on 2018-03-15.
 *
 * Description : Main Activity 현재 메인에 아무 동작이 없음
 */
public class MainActivity extends DefaultActivity {
    private DrawerLayout drawer;
//    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_bar_title_main);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set up the drawer.
        drawer = findViewById(R.id.drawer_layout);
        drawer.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = this.findViewById(R.id.drawer);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.mnu_action_01:
                        Toast.makeText(this, "MENU_ACTION_01", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mnu_action_02:
                        Toast.makeText(this, "MENU_ACTION_02", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                menuItem.setChecked(true);
                drawer.closeDrawers();
                return true;
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.mnu_search:
                startActivity(new Intent(this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
