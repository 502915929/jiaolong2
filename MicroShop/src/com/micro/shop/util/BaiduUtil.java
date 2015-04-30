package com.micro.shop.util;

import com.baidu.location.LocationClient;

/**
 * Created by 95 on 2015/4/28.
 */
public class BaiduUtil {

    public static void baiduMapStatus(LocationClient mLocClient){
        if(mLocClient.isStarted()){
            mLocClient.stop();
        }else{
            mLocClient.start();
        }
    }
}
