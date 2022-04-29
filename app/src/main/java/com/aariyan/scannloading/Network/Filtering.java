package com.aariyan.scannloading.Network;

import android.content.Context;

import com.aariyan.scannloading.Model.LinesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Filtering {

    private Context context;
    LinesModel m = null;

    public Filtering(Context context) {
        this.context = context;
    }

    public List<LinesModel> getFlagData(List<LinesModel> models, int flag) {
        List<LinesModel> l = new ArrayList<>();
        Observable observable = Observable.fromIterable(models).filter(model -> model.getFlag() == flag);
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                l.add((LinesModel) o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return l;
    }

    public LinesModel getLineByBarcode(List<LinesModel> models, String barcode, int flag) {
        Observable observable = Observable.fromIterable(models)
                .filter(model -> model.getBarCode().equals(barcode) && flag == 0);
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                m = (LinesModel) o;
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
        return m;
    }


}
