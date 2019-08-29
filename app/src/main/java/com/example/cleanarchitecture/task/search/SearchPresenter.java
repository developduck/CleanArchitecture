package com.example.cleanarchitecture.task.search;

import com.example.cleanarchitecture.task.search.domain.usecase.GetSearchedUsers;

/**
 * Created by Hwang on 2018-03-21.
 *
 * Description :
 */
public class SearchPresenter implements SearchContract.Presenter {
    private final SearchContract.View view;
    private final GetSearchedUsers getSearchedUsers;

    private int age;

    public SearchPresenter(int age, SearchContract.View view, GetSearchedUsers getSearchedUsers) {
        this.age = age;
        this.view = view;
        this.getSearchedUsers = getSearchedUsers;

        this.view.setPresenter(this);
    }

    @Override public void create() {
        if (verify()) {
            request();
        }
    }
    @Override public void resume() { }
    @Override public void pause() { }
    @Override public void destroy() {
        getSearchedUsers.dispose();
    }

    @Override
    public int getCurrentAge() {
        return age;
    }

    @Override
    public void onSearch() {
        age = view.getAge();
        if (verify()) {
            request();
        }
    }

    public void request() {
        getSearchedUsers.execute(view::show, view::error, new GetSearchedUsers.RequestValue(age));
    }
    public boolean verify() {
        return age > -1;
    }
}
