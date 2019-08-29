package com.example.cleanarchitecture.manage.network;

import android.util.Log;

import com.example.cleanarchitecture.data.model.Model;

import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Response;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description : Response 데이터를 공통으로 처리하기 위한 람다 옵저버
 */
public class FunkObserver<T extends Response<Model.Root<E>>, E> extends AtomicReference<Disposable>
        implements Observer<T>, Disposable, LambdaConsumerIntrospection {
    static final BiConsumer<Integer, String> ON_RESPONSE_ERROR_MISSING = new OnErrorMissingBiConsumer();

    static final class OnErrorMissingBiConsumer implements BiConsumer<Integer, String> {
        @Override
        public void accept(Integer integer, String s) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(new RuntimeException("Error Code:" + integer + ", Message:" + s)));
        }
    }

    private static final long serialVersionUID = -7251123623727029452L;
    private final Consumer<? super E> onResponseNext;
    private final BiConsumer<? super Integer, ? super String> onResponseError;
    private final Action onComplete;
    private final Consumer<? super Disposable> onSubscribe;

    public FunkObserver(Consumer<? super E> onResponseNext, BiConsumer<? super Integer, ? super String> onResponseError,
                 Action onComplete, Consumer<? super Disposable> onSubscribe) {
        super();
        this.onResponseNext = onResponseNext;
        this.onResponseError = onResponseError;
        this.onComplete = onComplete;
        this.onSubscribe = onSubscribe;
    }

    @Override
    public void onSubscribe(Disposable s) {
        if (DisposableHelper.setOnce(this, s)) {
            try {
                onSubscribe.accept(this);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                s.dispose();
                onError(ex);
            }
        }
    }
    @Override
    public void onNext(T response) {
        if (!isDisposed()) {
            try {
                int responseCode = response.code();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Model.Root<E> root = response.body();
                    onResponseNext.accept(root != null ? root.getData() : null);
                } else {
                    onError(responseCode, response.message());
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                get().dispose();
                onError(e);
            }
        }
    }
    @Override
    public void onError(Throwable t) {
        if (!isDisposed()) {
            lazySet(DisposableHelper.DISPOSED);
            try {
                Log.e("ERROR", this.getClass().getSimpleName(), t);
                onError(-1, t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(new CompositeException(t, e));
            }
        }
    }
    public void onError(int code, Throwable e) throws Throwable {
        onError(code, e.getMessage());
    }
    public void onError(int code, String message) throws Throwable {
        onResponseError.accept(code, message);
    }
    @Override
    public void onComplete() {
        if (!isDisposed()) {
            lazySet(DisposableHelper.DISPOSED);
            try {
                onComplete.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
        }
    }
    @Override
    public void dispose() {
        DisposableHelper.dispose(this);
    }
    @Override
    public boolean isDisposed() {
        return get() == DisposableHelper.DISPOSED;
    }
    @Override
    public boolean hasCustomOnError() {
        return onResponseError != ON_RESPONSE_ERROR_MISSING;
    }
}
