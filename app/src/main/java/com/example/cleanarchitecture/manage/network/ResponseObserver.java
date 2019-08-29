package com.example.cleanarchitecture.manage.network;

import android.util.Log;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.exception.ResponseFailedException;

import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Response;

/**
 * Created by Hwang on 2018-03-20.
 *
 * Description : Response 데이터를 공통으로 처리하기 위한 람다 옵저버
 */
public class ResponseObserver<E> extends AtomicReference<Disposable>
        implements Observer<E>, Disposable, LambdaConsumerIntrospection {
    private static final BiConsumer<Integer, Throwable> ON_RESPONSE_ERROR_MISSING = new OnErrorMissingBiConsumer();

    static final class OnErrorMissingBiConsumer implements BiConsumer<Integer, Throwable> {
        @Override
        public void accept(Integer integer, Throwable t) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(new RuntimeException(t)));
        }
    }

    private static final long serialVersionUID = -7251123623727029452L;
    private final Consumer<? super E> onResponseNext;
    private final BiConsumer<? super Integer, ? super Throwable> onResponseError;
    private final Consumer<? super Throwable> onError;
    private final Action onComplete;
    private final Consumer<? super Disposable> onSubscribe;

    public ResponseObserver(Consumer<? super E> onResponseNext, BiConsumer<? super Integer, ? super Throwable> onResponseError,
                            Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {
        super();
        this.onResponseNext = onResponseNext;
        this.onResponseError = onResponseError;
        this.onError = onError;
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
    public void onNext(E data) {
        if (!isDisposed()) {
            try {
                onResponseNext.accept(data);
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
                if (t instanceof ResponseFailedException) {
                    onResponseError.accept(((ResponseFailedException) t).getCode(), t);
                } else {
                    if (onError != null) { onError.accept(t); }
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(new CompositeException(t, e));
            }
        }
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
