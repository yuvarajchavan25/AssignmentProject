package com.android.assignment.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android.assignment.R;
import com.android.assignment.utils.AppUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Yuvaraj on 27-Oct-17.
 * <p/>
 * Volley different types of error handling to know the user
 */
public class VolleyErrorHandling {

    public VolleyErrorHandling() {

    }

    public static void checkErrorType(Object error,
                                      Context context) {
        if (error instanceof TimeoutError) {
            AppUtils.showToast(context, R.string.timeout_error);
        } else if (error instanceof ServerError) {
            handleServerError(error, context);
        } else if (error instanceof AuthFailureError) {
            AppUtils.showToast(context, R.string.auth_error);
        } else if (error instanceof NetworkError) {
            AppUtils.showToast(context, R.string.network_error);
        } else if (error instanceof NoConnectionError) {
            AppUtils.showToast(context, R.string.no_internet_connectivity);
        } else if (error instanceof ParseError) {
            AppUtils.showToast(context, R.string.parse_error);
        }

    }


    /**
     * Handles the server error, tries to determine whether to show a stock
     * message or to show a message retrieved from the server.
     *
     * @param err
     * @param context
     * @return
     */
    private static void handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 404:
                case 422:
                case 401:
                    AppUtils.showToast(context, R.string.server_error);
                    break;
                case 500:
                    AppUtils.showToast(context, R.string.something_wrong_error);
                    break;
                case 503:
                    AppUtils.showToast(context, R.string.unexpected_error);
                    break;
                default:
                    AppUtils.showToast(context, R.string.server_error);
                    break;

            }

        }
    }
}
