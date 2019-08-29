package com.example.cleanarchitecture;

import android.content.Context;

import com.example.cleanarchitecture.data.model.Const;
import com.example.cleanarchitecture.data.source.UsersRepository;
import com.example.cleanarchitecture.data.source.local.UsersLocalDataSource;
import com.example.cleanarchitecture.data.source.remote.UsersRemoteDataSource;
import com.example.cleanarchitecture.data.source.remote.retrofit.service.DefaultServiceGroup;
import com.example.cleanarchitecture.manage.Runner;
import com.example.cleanarchitecture.manage.db.ORDBM;
import com.example.cleanarchitecture.manage.network.retrofit.NetworkModule;
import com.example.cleanarchitecture.task.detail.domain.usecase.GetUser;
import com.example.cleanarchitecture.task.search.domain.usecase.GetSearchedUsers;

import java.io.File;

import retrofit2.Retrofit;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description : 의존성 주입(DI:Dependency Injection)을 위한 클래스(대거2 적용 전)
 */
public class Injection {
    public static Retrofit provideRetrofit(Context context) {
        return new NetworkModule(Const.URL.MAIN, new File(context.getCacheDir(), "responses"))
                .provideRetrofit();
    }
    public static DefaultServiceGroup.UserService provideUserService(Context context) {
        return provideRetrofit(context).create(DefaultServiceGroup.UserService.class);
    }
    public static UsersRepository provideUsersRepository(Context context) {
        return UsersRepository.getInstance(UsersRemoteDataSource.getInstance(provideUserService(context)),
                UsersLocalDataSource.getInstance(new Runner(), ORDBM.getInstance(context).userDao()));
    }
    public static GetUser provideGetUser(Context context) {
        return new GetUser(provideUsersRepository(context));
    }
    public static GetSearchedUsers provideGetSearchedUsers(Context context) {
        return new GetSearchedUsers(provideUsersRepository(context));
    }
}
