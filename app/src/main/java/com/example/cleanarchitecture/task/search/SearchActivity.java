package com.example.cleanarchitecture.task.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.example.cleanarchitecture.Injection;
import com.example.cleanarchitecture.R;
import com.example.cleanarchitecture.task.DefaultActivity;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description :
 */
public class SearchActivity extends DefaultActivity {
    private SearchPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_bar_title_search);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        SearchFragment fragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.content);

        if (fragment == null) {
            addFragment(fragment = SearchFragment.create(SearchFragment.class), R.id.content);
        }

        int age = -1;

        if (savedInstanceState != null) {
            age = savedInstanceState.getInt(this.getClass().getName() + ".age");
        }

        presenter = new SearchPresenter(
                age,
                fragment,
                Injection.provideGetSearchedUsers(getApplicationContext())
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(this.getClass().getName() + ".age", presenter.getCurrentAge());
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
