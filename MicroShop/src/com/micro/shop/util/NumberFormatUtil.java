package com.micro.shop.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by 95 on 2015/4/11.
 */
public class NumberFormatUtil {

    public static double m1(double f) {
        BigDecimal bg = new BigDecimal(f);
        f = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }

    /**
     * DecimalFormat转换最简便
     */
    public static double m2(double f) {
        DecimalFormat df = new DecimalFormat("#.00");
        f=Double.parseDouble(df.format(f));
        return f;
    }

    /**
     * String.format打印最简便
     */
    public static double m3(double f) {
        f=Double.parseDouble(String.format("%.2f", f));
        return  f;
    }

    public static double m4(double f) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        f=Double.parseDouble(nf.format(f));
        return f;
    }


    public static String conventToString(double f) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(f);
    }

    public static String conventToString1(double f) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(f);
    }
}
