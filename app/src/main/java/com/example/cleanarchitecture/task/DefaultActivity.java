package com.example.cleanarchitecture.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description :
 */
public class DefaultActivity extends AppCompatActivity {
    public void addFragment(@NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
