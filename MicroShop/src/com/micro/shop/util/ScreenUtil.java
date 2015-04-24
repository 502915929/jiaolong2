package com.micro.shop.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by 95 on 2015/4/24.
 * 获取屏幕宽高
 */
public class ScreenUtil {
    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
