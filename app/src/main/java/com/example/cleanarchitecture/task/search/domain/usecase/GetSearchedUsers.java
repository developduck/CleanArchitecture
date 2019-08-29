package com.example.cleanarchitecture.task.search.domain.usecase;

import androidx.annotation.NonNull;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.data.source.UsersDataSource;
import com.example.cleanarchitecture.data.source.UsersRepository;
import com.example.cleanarchitecture.task.UseCase;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Hwang on 2018-03-27.
 *
 * Description :
 */
public class GetSearchedUsers extends UseCase<GetSearchedUsers.RequestValue, List<Model.User>> {
    private UsersDataSource repository;

    public GetSearchedUsers(@NonNull UsersRepository repository) {
        super();
        this.repository = repository;
    }
    @Override
    protected Observable<List<Model.User>> buildUseCaseObservable(RequestValue params) {
        return repository.getSearchedUsers(params.getAge());
    }
    public static class RequestValue implements UseCase.RequestValue {
        private int age;

        public RequestValue(int age) {
            this.age = age;
        }
        public int getAge() {
            return age;
        }
    }
}
