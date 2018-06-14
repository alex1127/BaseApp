package com.ly.baseapp;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.ly.baseapp.base.BaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {


    @BindView(R.id.name)
    TextView username;

    @BindView(R.id.button)
    Button button;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.button)
    public void onViewClicked(View view) {
//        Intent intent = new Intent();
//        intent.setClass(this, OtherActivity.class);
//        startActivity(intent);

    }

}
