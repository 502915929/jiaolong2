package com.micro.shop.constant;

/**
 * Created by 95 on 2015/4/2.
 */
public interface ConstantJiao {

    String host_url ="http://192.168.1.119:8080/";
    String host="qqt_up/";

    /**
     * 上线图片存储路径
     */
    String aliUrl ="http://ossimg.178tb.com/";

    //**************************************************************************************************************//
    /**
     * 动态数据查询接口
     */
    String dynamicUrl =host_url+host+"dynamic/findNewMsg.htm";

    /**
     * 用户收藏商品接口
     */
    String userCollProUrl=host_url+host+"dynamic/saveUserCollectProduct.htm";

    /**
     * 用户点击动态商品城市地址跳转到百度地图店铺
     */
    String findShopMapMsgUrl=host_url+host+"dynamic/findMapMsg.htm";


    //****************************************************************************************************************//





    //====================================baidu==================================
    String ak ="B9346deadaa551216717a024ecf59d6f";
    int geoTableId=71401;
    //===========================================================================

}
