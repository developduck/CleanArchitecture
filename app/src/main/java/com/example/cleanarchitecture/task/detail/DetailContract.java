package com.example.cleanarchitecture.task.detail;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.task.BasePresenter;
import com.example.cleanarchitecture.task.BaseView;

/**
 * Created by Hwang on 2018-03-21.
 *
 * Description :
 */
public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void setLoading(boolean isLoading);
        boolean isActive();
        void show(Model.User user);
        void error(int code, Throwable e);
    }
    interface Presenter extends BasePresenter {
        void resume();
        void pause();
        int getUserId();
    }
}
