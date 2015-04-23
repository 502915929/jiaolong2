package com.micro.shop.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 获取连接网络的工具类
 * Created by 95 on 2015/4/2.
 */
public class HttpUtil {

    //实例话对象
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(11000); //设置链接超时，如果不设置，默认为10s
    }

    public static AsyncHttpClient getClient(){
        return client;
    }

}
