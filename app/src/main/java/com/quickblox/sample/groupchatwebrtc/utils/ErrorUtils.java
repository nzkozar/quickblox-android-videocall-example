package com.quickblox.sample.groupchatwebrtc.utils;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.quickblox.sample.groupchatwebrtc.App;
import com.quickblox.sample.groupchatwebrtc.R;

public class ErrorUtils {

    private static final String NO_CONNECTION_ERROR = "Connection failed. Please check your internet connection.";
    private static final String NO_RESPONSE_TIMEOUT = "No response received within reply timeout.";

    public static void showSnackbar(View view, @StringRes int errorMessage, Exception e,
                                        @StringRes int actionLabel, View.OnClickListener clickListener) {
        String error = (e == null) ? "" : e.getMessage();
        boolean noConnection = NO_CONNECTION_ERROR.equals(error);
        boolean timeout = error.startsWith(NO_RESPONSE_TIMEOUT);
        if (noConnection || timeout) {
            showSnackbar(view, R.string.no_internet_connection, actionLabel, clickListener);
        } else if (errorMessage == 0) {
            showSnackbar(view, error, actionLabel, clickListener);
        } else if (error.equals("")) {
            showSnackbar(view, errorMessage, App.getInstance().getString(R.string.no_internet_connection), actionLabel, clickListener);
        } else {
            showSnackbar(view, errorMessage, error, actionLabel, clickListener);
        }
    }

    private static Snackbar showSnackbar(View view, @StringRes int errorMessage, String error,
                                         @StringRes int actionLabel, View.OnClickListener clickListener) {
        String errorMessageString = App.getInstance().getString(errorMessage);
        String message = String.format("%s: %s", errorMessageString, error);
        return showSnackbar(view, message, actionLabel, clickListener);
    }

    public static Snackbar showSnackbar(View view, @StringRes int message,
                                        @StringRes int actionLabel,
                                        View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(actionLabel, clickListener);
        snackbar.show();
        return snackbar;
    }

    private static Snackbar showSnackbar(View view, String message,
                                         @StringRes int actionLabel,
                                         View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar.make(view, message.trim(), Snackbar.LENGTH_INDEFINITE);
        if (clickListener != null) {
            snackbar.setAction(actionLabel, clickListener);
        }
        snackbar.show();
        return snackbar;
    }
}
