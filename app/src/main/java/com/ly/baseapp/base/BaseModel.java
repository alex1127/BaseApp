package com.ly.baseapp.base;

import java.util.List;

public interface BaseModel {

    interface DataCallback<T> {

        void onSuccess(T t);
        void onError(int code,String message);

    }
}
