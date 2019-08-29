package com.example.cleanarchitecture.task.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.example.cleanarchitecture.Injection;
import com.example.cleanarchitecture.R;
import com.example.cleanarchitecture.task.DefaultActivity;
import com.example.cleanarchitecture.task.search.SearchFragment;

/**
 * Created by Hwang on 2018-03-26.
 *
 * Description :
 */
public class DetailActivity extends DefaultActivity {
    private DetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_bar_title_detail);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        DetailFragment fragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.content);

        if (fragment == null) {
            addFragment(fragment = SearchFragment.create(DetailFragment.class), R.id.content);
        }

        int userId = getIntent().getIntExtra(DetailFragment.USER_ID, -1);

        if (savedInstanceState != null) {
            userId = savedInstanceState.getInt(this.getClass().getName() + ".userId");
        }

        presenter = new DetailPresenter(
                userId,
                fragment,
                Injection.provideGetUser(getApplicationContext())
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(this.getClass().getName() + ".userId", presenter.getUserId());
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
