package com.oxfordplus.hypen.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontConstsant {

    public static final String FONT_BOLD = "fonts/mavenpro_bold.ttf";
    public static final String FONT_NORMAL = "fonts/mavenpro_regular.ttf";
    public static final String FONT_MEDIUM = "fonts/mavenpro_medium.ttf";

    public static Typeface getfontNormal(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FontConstsant.FONT_NORMAL);
    }

    public static Typeface getfontBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FontConstsant.FONT_BOLD);
    }
}
