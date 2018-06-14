package com.ly.baseapp.user.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ly.baseapp.R;
import com.ly.baseapp.base.BaseActivity;
import com.ly.baseapp.bean.LoginParams;
import com.ly.baseapp.bean.LoginResult;
import com.ly.baseapp.user.contract.LoginContract;
import com.ly.baseapp.user.model.LoginModel;
import com.ly.baseapp.user.presenter.LoginPresenter;
import com.ly.baseapp.util.DaoUtil;
import com.ly.baseapp.util.SPDao;
import com.ly.baseapp.util.Utils;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements LoginContract.View {


    @Inject
    SPDao spDao;


    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.cardview)
    CardView cardview;

    @BindView(R.id.fab_btn)
    FloatingActionButton fabBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        etPassword.setText("qwer1234");
        etUsername.setText("18601676820");

    }

    /**
     * Login ,普通的登录和使用Rxjava 的方式都可以
     */
    @OnClick(R.id.login_btn)
    public void mvpLogin() {
        String userName = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toasty.error(this.getApplicationContext(), "请完整输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        //1.需要改进，能否改进为链式写法

        Map<String, String> map = new TreeMap<String, String>();
        map.put("username", userName);
        map.put("passwd", Utils.encodeByMD5(password));

        loginPresenter.login(userName, Utils.encodeByMD5(password), DaoUtil.getInstance().getToken(this, map), DaoUtil.getInstance().getCurrentTime());
    }


    /**
     * 跳转到注册®️
     */
    @OnClick(R.id.fab_btn)
    public void goRegister() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fabBtn, fabBtn.getTransitionName());
            startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    @Override
    public void loginSuccess(LoginResult loginBean) {

    }

    @Override
    public void loginFail(String failMsg) {

    }


}
