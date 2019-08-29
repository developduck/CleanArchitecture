package com.example.cleanarchitecture.task;

/**
 * Created by Hwang on 2018-03-21.
 *
 * Description :
 */
public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
