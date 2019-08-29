package com.example.cleanarchitecture.task.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanarchitecture.R;
import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.task.DefaultFragment;
import com.example.cleanarchitecture.task.detail.DetailActivity;
import com.example.cleanarchitecture.task.detail.DetailFragment;
import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;

import java.util.List;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description :
 */
public class SearchFragment extends DefaultFragment implements SearchContract.View {
    @Bind private EditText edtAge;

    private SearchAdapter adapter;
    private SearchContract.Presenter presenter;

    @Override
    public View onCreateView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Binder.bind(view, this)
                .onClick(v -> presenter.onSearch(), R.id.btn_search);
        RecyclerView listSearched = view.findViewById(R.id.list_searched);

        listSearched.setLayoutManager(new LinearLayoutManager(getActivity()));
        listSearched.setAdapter(adapter = new SearchAdapter(getActivity())
                .setOnItemClick((v, position, obj) -> startActivity(new Intent(getActivity(), DetailActivity.class)
                        .putExtra(DetailFragment.USER_ID, obj.getId()))));

        edtAge.setOnEditorActionListener((v, actionId, event) -> {
            presenter.onSearch();
            return false;
        });
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
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public int getAge() {
        return Integer.parseInt(edtAge.getText().toString());
    }
    @Override
    public boolean isActive() {
        return isAdded();
    }
    @Override
    public void show(List<Model.User> list) {
        adapter.setItems(list);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void error(int code, Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
