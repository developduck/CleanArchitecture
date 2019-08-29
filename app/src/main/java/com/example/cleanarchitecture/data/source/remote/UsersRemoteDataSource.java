package com.example.cleanarchitecture.data.source.remote;

import androidx.annotation.NonNull;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.data.source.remote.retrofit.service.DefaultServiceGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description :
 */
public class UsersRemoteDataSource extends DefaultRemoteDataSource {
    private static UsersRemoteDataSource instance;

    private DefaultServiceGroup.UserService userService;

    private UsersRemoteDataSource(@NonNull DefaultServiceGroup.UserService userService) {
        this.userService = userService;
    }
    public synchronized static UsersRemoteDataSource getInstance(DefaultServiceGroup.UserService userService) {
        if (instance == null) {
            instance = new UsersRemoteDataSource(userService);
        }
        return instance;
    }

    public Observable<List<Model.User>> getUsers() {
        return userService.getUsers(new HashMap<>()).map(this::response);
    }
    public Observable<List<Model.User>> getSearchedUsers(int age) {
        return userService.getUsers(new HashMap<String, String>() {{
            put("age", String.format(Locale.getDefault(), "%d", age));
        }}).map(this::response);
    }
    public Observable<Model.User> getUser(int id) {
        return userService.getUser(id).map(this::response);
    }
}
