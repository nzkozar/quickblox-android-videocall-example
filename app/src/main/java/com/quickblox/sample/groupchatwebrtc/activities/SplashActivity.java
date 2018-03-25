package com.quickblox.sample.groupchatwebrtc.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;

import com.quickblox.sample.groupchatwebrtc.App;
import com.quickblox.sample.groupchatwebrtc.R;
import com.quickblox.sample.groupchatwebrtc.services.CallService;
import com.quickblox.sample.groupchatwebrtc.utils.ErrorUtils;
import com.quickblox.sample.groupchatwebrtc.utils.SharedPrefsHelper;
import com.quickblox.users.model.QBUser;

public class SplashActivity extends BaseActivity {
    private static final int SPLASH_DELAY = 1500;

    private static Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private SharedPrefsHelper sharedPrefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_splash);

        sharedPrefsHelper = SharedPrefsHelper.getInstance();

        if (sharedPrefsHelper.hasQbUser()) {
            startLoginService(sharedPrefsHelper.getQbUser());
            startOpponentsActivity();
            return;
        }

        if (checkConfigsWithSnackebarError()) {
            proceedToTheNextActivityWithDelay();
        }
    }

    @Override
    protected View getSnackbarAnchorView() {
        return null;
    }

    protected void proceedToTheNextActivity() {
        LoginActivity.start(this);
        finish();
    }

    protected void startLoginService(QBUser qbUser) {
        CallService.start(this, qbUser);
    }

    private void startOpponentsActivity() {
        OpponentsActivity.start(SplashActivity.this, false);
        finish();
    }

    protected boolean sampleConfigIsCorrect(){
        return App.getInstance().getQbConfigs() != null;
    }

    protected void proceedToTheNextActivityWithDelay() {
        mainThreadHandler.postDelayed(this::proceedToTheNextActivity, SPLASH_DELAY);
    }

    protected boolean checkConfigsWithSnackebarError(){
        if (!sampleConfigIsCorrect()){
            showSnackbarErrorParsingConfigs();
            return false;
        }

        return true;
    }

    protected void showSnackbarErrorParsingConfigs(){
        ErrorUtils.showSnackbar(findViewById(R.id.layout_root), R.string.error_parsing_configs, R.string.dlg_ok, null);
    }
}