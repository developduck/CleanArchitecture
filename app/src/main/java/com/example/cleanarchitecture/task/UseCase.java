package com.example.cleanarchitecture.task;

import com.example.cleanarchitecture.data.model.Model;
import com.example.cleanarchitecture.manage.network.FunkObserver;
import com.example.cleanarchitecture.manage.network.ResponseObserver;
import com.google.common.base.Preconditions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Hwang on 2018-04-16.
 * <p>
 * Description :
 */
public abstract class UseCase<PARAM, T> {
    /*
    프로젝트가 커지면 쓰레드를 스케줄링해야 할지도 몰라서 남겨둠
     */
//    private final ThreadExecutor threadExecutor;
//    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    protected UseCase(/*ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread*/) {
//        this.threadExecutor = threadExecutor;
//        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /*
    람다형 옵저버에서 일괄적으로 처리되는 것은 깔끔하고 괜찮으나 Response와 Root 모델로 감싸준 상태로 UseCase까지 와야함
    실제 프레젠터의 코드 자체는 깔끔하나 그전 데이터 소스의 코드가 지저분함
     */
//    protected abstract Observable<Response<Model.Root<T>>> buildUseCaseObservable(PARAM params);
//
//    public void execute(DisposableObserver<Response<Model.Root<T>>> observer, PARAM params) {
//        Preconditions.checkNotNull(observer);
//        final Observable<Response<Model.Root<T>>> observable = this.buildUseCaseObservable(params)
//                .subscribeOn(Schedulers.io()/*Schedulers.from(threadExecutor)*/)
//                .observeOn(AndroidSchedulers.mainThread()/*postExecutionThread.getScheduler()*/);
//        addDisposable(observable.subscribeWith(observer));
//    }
//    public void execute(Consumer<? super T> onResponseNext, BiConsumer<? super Integer, ? super String> onResponseError, PARAM params) {
//        execute(onResponseNext, onResponseError, Functions.EMPTY_ACTION, Functions.emptyConsumer(), params);
//    }
//    public void execute(Consumer<? super T> onResponseNext, BiConsumer<? super Integer, ? super String> onResponseError,
//                        Action onComplete, PARAM params) {
//        execute(onResponseNext, onResponseError, onComplete, Functions.emptyConsumer(), params);
//    }
//    public void execute(Consumer<? super T> onResponseNext,  BiConsumer<? super Integer, ? super String> onResponseError,
//                        Action onComplete, Consumer<? super Disposable> onSubscribe, PARAM params) {
//        ObjectHelper.requireNonNull(onResponseNext, "onResponseNext is null");
//        ObjectHelper.requireNonNull(onResponseError, "onResponseError is null");
//        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
//        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");
//
//        FunkObserver<Response<Model.Root<T>>, T> ls = new FunkObserver<>(onResponseNext, onResponseError, onComplete, onSubscribe);
//
//        addDisposable(this.buildUseCaseObservable(params)
//                .subscribeOn(Schedulers.io()/*Schedulers.from(threadExecutor)*/)
//                .observeOn(AndroidSchedulers.mainThread()/*postExecutionThread.getScheduler()*/)
//                .subscribeWith(ls));
//    }

    /*
    RxJava를 이용해서 Response를 일괄적으로 하나의 함수로 모으고 처리함, 코드가 깔끔해지지만.. Exception 처리가 무언가 마음에 안듬
     */
//    protected abstract Observable<T> buildUseCaseObservable(PARAM params);
//
//    public void execute(Consumer<? super T> onNext, Consumer<? super Throwable> onError, PARAM params) {
//        execute(onNext, onError, Functions.EMPTY_ACTION, Functions.emptyConsumer(), params);
//    }
//    public void execute(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
//                        Action onComplete, PARAM params) {
//        execute(onNext, onError, onComplete, Functions.emptyConsumer(), params);
//    }
//    public void execute(Consumer<? super T> onNext,  Consumer<? super Throwable> onError,
//                        Action onComplete, Consumer<? super Disposable> onSubscribe, PARAM params) {
//        addDisposable(this.buildUseCaseObservable(params)
//                .subscribeOn(Schedulers.io()/*Schedulers.from(threadExecutor)*/)
//                .observeOn(AndroidSchedulers.mainThread()/*postExecutionThread.getScheduler()*/)
//                .subscribe(onNext, onError, onComplete, onSubscribe));
//    }

    /*
    위에꺼 두개 섞어볼까.. 해서 섞었는데 만족스럽지는 않음
     */
    protected abstract Observable<T> buildUseCaseObservable(PARAM params);

    public void execute(Consumer<? super T> onResponseNext, BiConsumer<? super Integer, ? super Throwable> onResponseError, PARAM params) {
        execute(onResponseNext, onResponseError, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer(), params);
    }
    public void execute(Consumer<? super T> onResponseNext, BiConsumer<? super Integer, ? super Throwable> onResponseError, Consumer<? super Throwable> onError,
                        Action onComplete, PARAM params) {
        execute(onResponseNext, onResponseError, onError, onComplete, Functions.emptyConsumer(), params);
    }
    public void execute(Consumer<? super T> onResponseNext, BiConsumer<? super Integer, ? super Throwable> onResponseError,
                        Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe, PARAM params) {
        ObjectHelper.requireNonNull(onResponseNext, "onResponseNext is null");
        ObjectHelper.requireNonNull(onResponseError, "onResponseError is null");
        ObjectHelper.requireNonNull(onError, "onResponseError is null");
        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");

        ResponseObserver<T> ls = new ResponseObserver<>(onResponseNext, onResponseError, onError, onComplete, onSubscribe);

        addDisposable(this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io()/*Schedulers.from(threadExecutor)*/)
                .observeOn(AndroidSchedulers.mainThread()/*postExecutionThread.getScheduler()*/)
                .subscribeWith(ls));
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
    public interface RequestValue {}
}