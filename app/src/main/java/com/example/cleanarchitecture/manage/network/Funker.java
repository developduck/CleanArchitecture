package com.example.cleanarchitecture.manage.network;

import com.example.cleanarchitecture.data.model.Model;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Hwang on 2018-03-19.
 *
 * Description : 통신 모듈 랩퍼 클래스
 */
public class Funker {
    protected <E> void subscribe(Observable<Response<Model.Root<E>>> observable, Consumer<? super E> onResponseNext) {
        subscribe(observable, onResponseNext, FunkObserver.ON_RESPONSE_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer());
    }
    protected <E> void subscribe(Observable<Response<Model.Root<E>>> observable, Consumer<? super E> onResponseNext,
                               BiConsumer<? super Integer, ? super String> onResponseError) {
        subscribe(observable, onResponseNext, onResponseError, Functions.EMPTY_ACTION, Functions.emptyConsumer());
    }
    private <E> void subscribe(Observable<Response<Model.Root<E>>> observable, Consumer<? super E> onResponseNext,
                               BiConsumer<? super Integer, ? super String> onResponseError,
                               Action onComplete, Consumer<? super Disposable> onSubscribe) {
        ObjectHelper.requireNonNull(onResponseNext, "onResponseNext is null");
        ObjectHelper.requireNonNull(onResponseError, "onResponseError is null");
        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");

        FunkObserver<Response<Model.Root<E>>, E> ls = new FunkObserver<>(onResponseNext, onResponseError, onComplete, onSubscribe);

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ls);
    }
}
