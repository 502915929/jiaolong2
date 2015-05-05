package com.micro.shop.constant;

/**
 * Created by 95 on 2015/4/2.
 */
public interface ConstantJiao {

    String host_url ="http://192.168.1.119:8080/";
   // String host_url ="http://192.168.1.108:8080/";
   //String host_url ="http://192.168.1.161:8080/";
  // String host_url ="http://192.168.1.169:8080/";
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

    /**
     * 用户进入店铺首页加载首页数据url
     */
    String showShopIndexUrl=host_url+host+"dynamic/shopIndex.htm";

    /**
     * 用户进入商品资料页查看商品资料
     */
    String showProDetailUrl=host_url+host+"dynamic/productDetail.htm";


    String proDetailTab_one_url=host_url+host+"dynamic/findProductAttributeList.htm";

    /**
     * 加载所有图片详情 （参数  productCode,start,number）
     */
    String proDetailTab_two_url=host_url+host+"dynamic/findImageList.htm";

    /**
     * 根据定位坐标返回本地周边商圈商品列表
     */
    String localProListUrl=host_url+host+"dynamic/findLocalProList.htm";

    /**
     * 搜索数据接口（列表）
     */
    String searchShopListUrl=host_url+host+"dynamic/findSearchShopList.htm";

    /**
     * 用户首次打开app上传设备信息接口
     */
    String fristOpenAppUrl=host_url+host+"dynamic/saveDeviceMsg.htm";
    //****************************************************************************************************************//





    //====================================baidu==================================
    String ak ="B9346deadaa551216717a024ecf59d6f";
    int geoTableId=71401;
    //===========================================================================

}
