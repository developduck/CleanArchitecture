package com.example.cleanarchitecture.task.search;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.task.BasePresenter;
import com.example.cleanarchitecture.task.BaseView;

import java.util.List;

/**
 * Created by Hwang on 2018-03-21.
 *
 * Description :
 */
public interface SearchContract {
    interface View extends BaseView<Presenter> {
        int getAge();
        boolean isActive();
        void show(List<Model.User> list);
        void error(int code, Throwable e);
    }
    interface Presenter extends BasePresenter {
        void resume();
        void pause();
        int getCurrentAge();
        void onSearch();
    }
}
