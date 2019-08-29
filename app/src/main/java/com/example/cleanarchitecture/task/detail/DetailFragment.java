package com.example.cleanarchitecture.task.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cleanarchitecture.R;
import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.task.DefaultFragment;
import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;

import java.util.Locale;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description :
 */
public class DetailFragment extends DefaultFragment implements DetailContract.View {
    public static final String USER_ID = "USER_ID";

    @Bind private TextView txtId;
    @Bind private TextView txtName;
    @Bind private TextView txtAge;
    @Bind private TextView txtNationality;
    @Bind private ProgressBar progress;

    private DetailContract.Presenter presenter;

    @Override
    public View onCreateView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Binder.bind(view, this);
        presenter.create();
        return super.onCreateView(view, inflater, container, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }
    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public void setLoading(boolean isLoading) {
        progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    @Override
    public boolean isActive() {
        return isAdded();
    }
    @Override
    public void show(Model.User user) {
        txtId.setText(String.format(Locale.getDefault(), "%d", user.getId()));
        txtName.setText(user.getName());
        txtAge.setText(String.format(Locale.getDefault(), "%d", user.getAge()));
        txtNationality.setText(user.getNationality());
    }
    @Override
    public void error(int code, Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
