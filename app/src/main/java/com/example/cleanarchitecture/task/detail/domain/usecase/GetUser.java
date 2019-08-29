package com.example.cleanarchitecture.task.detail.domain.usecase;

import androidx.annotation.NonNull;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.data.source.UsersDataSource;
import com.example.cleanarchitecture.data.source.UsersRepository;
import com.example.cleanarchitecture.task.UseCase;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Hwang on 2018-03-23.
 *
 * Description :
 */
public class GetUser extends UseCase<GetUser.RequestValue, Model.User> {
    private final UsersDataSource repository;

    public GetUser(@NonNull UsersRepository repository) {
        this.repository = checkNotNull(repository);
    }
    @Override
    protected Observable<Model.User> buildUseCaseObservable(RequestValue params) {
        return repository.getUser(params.getUserId());
    }
    public static class RequestValue implements UseCase.RequestValue {
        private final int id;

        public RequestValue(int userId) {
            this.id = userId;
        }
        public int getUserId() {
            return id;
        }
    }
}
