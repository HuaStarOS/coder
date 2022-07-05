package com.qzh.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: qzh
 * @Date: 2021/4/8 16:04
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    // 读取配置文件中内容

    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;


    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    /**
     * afterPropertiesSet方法 会在spring加载组件之后执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID=this.keyId;
        KEY_SECRET=this.keySecret;
        END_POINT=this.endPoint;
        BUCKET_NAME=this.bucketName;
    }

}
