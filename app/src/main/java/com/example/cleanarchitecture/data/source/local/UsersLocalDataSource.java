package com.example.cleanarchitecture.data.source.local;

import androidx.annotation.NonNull;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.data.source.local.dao.DefaultDaoGroup;
import com.example.cleanarchitecture.manage.Runner;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description :
 */
public class UsersLocalDataSource {
    private static UsersLocalDataSource instance;

    private DefaultDaoGroup.UserDao userDao;
    private Runner runner;

    private UsersLocalDataSource(@NonNull Runner runner, @NonNull DefaultDaoGroup.UserDao userDao) {
        this.runner = runner;
        this.userDao = userDao;
    }
    public synchronized static UsersLocalDataSource getInstance(@NonNull Runner runner, @NonNull DefaultDaoGroup.UserDao userDao) {
        if (instance == null) {
            instance = new UsersLocalDataSource(runner, userDao);
        }
        return instance;
    }

    public void getUsers() {
    }
    public void getUser(int id) {
    }
    public void cacheUsers(@NonNull Model.User user) {
        runner.local().execute(() -> userDao.insert(user));
    }
}
