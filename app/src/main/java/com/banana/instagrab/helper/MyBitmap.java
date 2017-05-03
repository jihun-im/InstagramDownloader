package com.banana.instagrab.helper;

import android.graphics.Bitmap;

/**
 * Created by Jihun on 2017-03-08.
 */

public class MyBitmap {
    Bitmap mBitmap;
    String mString;

    public MyBitmap(Bitmap bmp, String imgName) {
        mBitmap = bmp;
        mString = imgName;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public String getString() {

        return mString;
    }

}
