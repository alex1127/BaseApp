package com.ly.baseapp.http.status;


import com.kingja.loadsir.callback.Callback;
import com.ly.baseapp.R;


public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
