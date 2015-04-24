package com.micro.shop.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by 95 on 2015/4/24.
 * ��ȡ��Ļ���
 */
public class ScreenUtil {
    //��ȡ��Ļ�Ŀ��
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    //��ȡ��Ļ�ĸ߶�
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
