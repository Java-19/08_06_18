package com.sheygam.java_19_08_06_18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        creation();
//        diposableTest();
        types();
    }

    public void creation(){
        ObservableOnSubscribe<String> onSubscribe = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("Vasya");
//                emitter.onNext("Petya");
//                emitter.onNext("Vova");
////                emitter.onComplete();
//                emitter.onError(new Exception("Exception"));

                for (int i = 0; i < 10; i++) {
                    emitter.onNext(String.valueOf(i));
                }
                emitter.onComplete();
            }
        };

        Observable<String> observable = Observable.create(onSubscribe);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("MY_TAG", "onSubscribe() called with: d = [" + d + "]");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext() called with: s = [" + s + "]");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete() called");
            }
        };

        observable.subscribe(observer);

        observable = Observable.just("Vasya","Petya","Vova");
        observable.subscribe(observer);

        String[] array = {"Vasya","Petya","Vova"};

        observable = Observable.fromArray(array);
        observable.subscribe(observer);

        List<String> list = Arrays.asList(array);
        observable = Observable.fromIterable(list);
        observable.subscribe(observer);

        observable = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Vasya";
            }
        });

        observable.subscribe(observer);
    }

    public void diposableTest(){
        DisposableObserver<String> observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext() called with: s = [" + s + "]");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete() called");
            }
        };

        Observable<String> observable = Observable.just("Vasya","Peta","Sofa");
        observable.subscribe(observer);
    }

    public void types(){

        Single<String> single = Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "Vasya";
            }
        });

        DisposableSingleObserver<String> singleObserver = new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "onSuccess() called with: s = [" + s + "]");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }
        };

        single.subscribe(singleObserver);

        Completable completable = Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
//                int[] arr = new int[10];
//                arr[10] = 1;
            }
        });

        DisposableCompletableObserver completableObserver = new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }
        };

        completable.subscribe(completableObserver);

        Maybe<String> maybe = Maybe.just("Vasya");

        DisposableMaybeObserver<String> maybeObserver = new DisposableMaybeObserver<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "onSuccess() called with: s = [" + s + "]");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete() called");
            }
        };

        maybe.subscribe(maybeObserver);
    }

}
