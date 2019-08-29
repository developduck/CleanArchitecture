package com.example.cleanarchitecture.data.source;

import androidx.annotation.NonNull;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.data.source.local.UsersLocalDataSource;
import com.example.cleanarchitecture.data.source.remote.UsersRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description :
 */
public class UsersRepository implements UsersDataSource {
    private static UsersRepository instance;
    private final UsersRemoteDataSource remote;
    private final UsersLocalDataSource local;

    private UsersRepository(@NonNull UsersRemoteDataSource remote, @NonNull UsersLocalDataSource local) {
        this.remote = checkNotNull(remote);
        this.local = checkNotNull(local);
    }
    public synchronized static UsersRepository getInstance(@NonNull UsersRemoteDataSource remote, @NonNull UsersLocalDataSource local) {
        if (instance == null) {
            instance = new UsersRepository(remote, local);
        }
        return instance;
    }

    @Override
    public Observable<List<Model.User>> getUsers() {
        return remote.getUsers();
    }
    @Override
    public Observable<List<Model.User>> getSearchedUsers(int age) {
        return remote.getSearchedUsers(age);
    }
    @Override
    public Observable<Model.User> getUser(int id) {
        return remote.getUser(id);
    }

    @Override
    public void cacheUsers(@NonNull Model.User user) {
        local.cacheUsers(user);
    }
}
