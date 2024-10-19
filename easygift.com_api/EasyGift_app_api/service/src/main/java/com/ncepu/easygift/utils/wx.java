package com.ncepu.easygift.utils;
/*
@Coding: utf-8
@Author: yizhigopher
@Time: 2023/1/6 10:56
@Software: IntelliJ IDEA
*/

import com.ncepu.easygift.exception.XzException;
import com.ncepu.easygift.result.REnum;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class wx {
    public static String GetOpenID(String code) throws XzException{
        String appID = "wx28daaf963a62af94";
        String appSecret = "80364337a4e85bfc767c155da7f787a1";
        //第一步：前端需要获取到用户同意授权的code给你传过来【code不用你管，拿String接收传来的参数即可】
        //url中的参数说明：
        //appId：不用解释，你懂得
        //appSecret: 不用解释，你懂得
        //code：前端传来的code
        String strURL = "https://api.weixin.qq.com/sns/jscode2session?appid="+appID+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
        //第二步：发起请求【copy就行了】
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            //开始发送请求
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.flush();
            out.close();
            int ResponseCode = connection.getResponseCode();
            InputStream is = null;
            if (ResponseCode == 200) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            // 第三步：读取响应
            ///在这里说一下，获取微信返回的方式有两种，随便挑一种即可
            //第一种：获取返回信息方式【比较骚气，用byte解析后获取】
            int length = (int) connection.getContentLength();// 获取长度
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                Map<String, Object> resultMap = com.alibaba.fastjson.JSONObject.parseObject(result);
                //【这里是你获取到openId的逻辑判断】
                //【plifeUser是我当时的用户实体类，保存openId，复制到你的项目可以删掉】
                return (String) resultMap.get("openid");
            }
            throw new XzException(REnum.InternalError.getCode(), "获取openid失败");
        } catch (IOException e) {
            throw new XzException(REnum.InternalError.getCode(), "获取openid失败");
        }
    }
}
