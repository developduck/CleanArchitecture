package com.example.cleanarchitecture.task.detail;

import com.example.cleanarchitecture.exception.ResponseFailedException;
import com.example.cleanarchitecture.task.detail.domain.usecase.GetUser;

/**
 * Created by Hwang on 2018-03-21.
 *
 * Description :
 */
public class DetailPresenter implements DetailContract.Presenter {
    private final DetailContract.View view;
    private final GetUser getUser;

    private int userId;

    public DetailPresenter(int userId, DetailContract.View view, GetUser getUser) {
        this.userId = userId;
        this.view = view;
        this.getUser = getUser;

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
        getUser.dispose();
    }

    @Override
    public int getUserId() {
        return userId;
    }

    public void request() {
        view.setLoading(true);
        getUser.execute(user -> {
            if (!view.isActive()) {
                return;
            }
            view.setLoading(false);
            view.show(user);
        }, view::error, new GetUser.RequestValue(userId));
    }
    public boolean verify() {
        return userId > -1;
    }
}
