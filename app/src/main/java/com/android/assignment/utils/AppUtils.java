package com.android.assignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.assignment.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Yuvaraj on 27-Oct-17.
 */

public class AppUtils {

    /**
     * Show toast to center of screen
     *
     * @param context   Activity context
     * @param messageId Toast message id to display
     */
    public static void showToast(Context context,
                                 int messageId) {
        Toast toast = Toast.makeText(context, context.getResources().getString(messageId), Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Set the image using Picasso
     *
     * @param context      activity context
     * @param imageLink    image link coming from server
     * @param profileImage image view to set image
     */
    public static void setImage(Context context,
                                String imageLink, ImageView profileImage) {
        if (!imageLink.equals("")) {
            Picasso.with(context)
                    .load(imageLink)
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
                    .into(profileImage);
        }
    }

}


