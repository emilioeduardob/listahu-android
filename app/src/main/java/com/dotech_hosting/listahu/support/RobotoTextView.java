package com.dotech_hosting.listahu.support;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dotech_hosting.listahu.MainApp;

/**
 * Created by emilio on 10/30/15.
 */
public class RobotoTextView extends TextView {
    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        setTypeface(MainApp.getRobotoRegular());
    }
}
