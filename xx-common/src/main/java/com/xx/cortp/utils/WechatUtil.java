package com.xx.cortp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wp on 2016/3/15.
 * 微信工具类
 */
public class WechatUtil {

    private  static  Map<String, Long> tokenMap = new HashMap<String, Long>();// 微信令牌

    private static  Map<String, Long> jsapiTicketMap = new HashMap<String, Long>();// 微信授权票据

    //网页授权接口地址
    public static String GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    private static String token = "";
    private static String jsapiTicket = "";

    private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * 获取微信的jsSDK token
     * @return String
     * @throws IOException
     */
    public static String getAccess_token(String appid,String secret) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + appid + "&secret=" + secret;
        String accessToken = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject  weChatJson = JSON.parseObject(message);
            accessToken = weChatJson.getString("access_token");
            token = accessToken;
            tokenMap.put("token", new Date().getTime() / 1000);
        } catch (Exception e) {
            logger.error("获取微信的jsSDK token异常：{}", e);
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 生产微信jsapiTicket 临时票据
     *
     * @param accessToken
     * @param accessToken
     * @throws IOException
     */
    public static  String getJsapi_ticket(String accessToken) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                + accessToken + "&type=jsapi";
        String JsapiTicket = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject  demoJson=  JSON.parseObject(message);
            JsapiTicket = demoJson.getString("ticket");
            jsapiTicketMap.put("JsapiTicket", new Date().getTime() / 1000);
            jsapiTicket = JsapiTicket;
        } catch (Exception e) {
            logger.error("生产微信jsapiTicket 临时票据：{}", e);
            e.printStackTrace();
        }
        return JsapiTicket;
    }

    /**
     * 用SHA1算法生成安全签名
     *
     * @param token
     *            票据
     * @param timestamp
     *            时间戳
     * @param nonce
     *            随机字符串
     * @param url
     * @return 安全签名
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getSHA1(String token, String timestamp, String nonce,
                          String url) throws NoSuchAlgorithmException, IOException {
        String[] paramArr = new String[] { "jsapi_ticket=" + token,
                "timestamp=" + timestamp, "noncestr=" + nonce, "url=" + url };
        Arrays.sort(paramArr);
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&" + paramArr[1])
                .concat("&" + paramArr[2]).concat("&" + paramArr[3]);
        logger.warn("生成签名string1" + content);
        // SHA1签名生成
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(content.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        System.out.println(hexstr.toString());
        logger.warn("生成签名SH1加密" + hexstr.toString());
        return hexstr.toString();
    }

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String token,String signature, String timestamp,
                                  String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        logger.warn("加密后-->" + tmpStr);
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 获取JSSDK初始化参数
     *
     * @param url
     * @param request
     * @return
     */
    public static  Map<String, String> getJssdkinfo(String url,String appid,String secret) {
        Map<String, String> jssdkInfoMap = new HashMap<String, String>();
        // 生产token jsapiTicket 签名
        String token = "";
        String getSHA1 = "";
        Date date = new Date();
        String timestampStr = String.valueOf(date.getTime() / 1000);
        String nonceStr = String.valueOf(UUID.randomUUID());
        String jsapiTicketStr = "";
        logger.info("url" + url);
        try {
            if ("".equals(token)
                    || null == token) {
                token = getAccess_token(appid,secret);// 微信授权令牌
            } else {
                if ((new Date().getTime() / 1000) - tokenMap.get("token") >= 7000) {
                    // 说明令牌已经失效
                    token = getAccess_token(appid,secret);// 微信授权令牌
                } else {
                    token = token;
                }
            }
            if ("".equals(jsapiTicket)
                    || null == jsapiTicket) {
                jsapiTicket = "jsapi_ticket=" + getJsapi_ticket(token);// 微信授权令牌
                jsapiTicketStr = getJsapi_ticket(token);
            } else {
                if ((new Date().getTime() / 1000)
                        - jsapiTicketMap.get("JsapiTicket") >= 7000) {
                    // 说明令牌已经失效
                    jsapiTicket = "jsapi_ticket=" + getJsapi_ticket(token);// 微信授权令牌;
                    jsapiTicketStr = getJsapi_ticket(token);
                } else {
                    jsapiTicketStr   = jsapiTicket;
                }
            }
            getSHA1 = getSHA1(jsapiTicketStr, timestampStr, nonceStr, url);
        } catch (Exception e) {
            logger.error("生成微信令牌异常:{}", e);
        }
        jssdkInfoMap.put("timestamp", timestampStr);
        jssdkInfoMap.put("nonce", nonceStr);
        jssdkInfoMap.put("getSHA1", getSHA1);
        jssdkInfoMap.put("appid", appid);
        return jssdkInfoMap;
    }

    /**
     * 获取用户的accessToken
     *
     * @param code
     * @return
     * @throws IOException
     */
    public static Map<String, Object> getUser_access_token(String code,String appid,String secret)
            throws IOException {
        Map<String, Object> userAccessTokenMap = new HashMap<String, Object>();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + appid + "&secret=" + secret + "&code=" + code
                + "&grant_type=authorization_code";
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject  demoJson = JSON.parseObject(message);
            userAccessTokenMap
                    .put("access_token", demoJson.get("access_token"));
            userAccessTokenMap.put("refresh_token",
                    demoJson.get("refresh_token"));
            userAccessTokenMap.put("openid", demoJson.get("openid"));
        } catch (Exception e) {
            logger.error("获取用户的accessToken异常:{}", e);
            e.printStackTrace();
        }
        return userAccessTokenMap;
    }


    /**
     * 获取用户信息
     *
     * @param userAccessTokenMap
     * @return
     * @throws IOException
     */
    public static  Map<String, Object> getUserInfo(
            Map<String, Object> userAccessTokenMap) throws IOException {
        Map<String, Object> userInfoMap = new HashMap<String, Object>();
        String access_token = (String) userAccessTokenMap.get("access_token");
        String openid = (String) userAccessTokenMap.get("openid");
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token + "&openid=" + openid + "&lang=zh_CN";
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSON.parseObject(message);
            userInfoMap.put("openid", demoJson.get("openid"));
            userInfoMap.put("nickname", demoJson.get("nickname"));
            userInfoMap.put("sex", demoJson.get("sex"));
            userInfoMap.put("city", demoJson.get("city"));
            userInfoMap.put("headimgurl", demoJson.get("headimgurl"));
            logger.info("用户信息接口查询头像地址-->" + demoJson.get("headimgurl"));
        } catch (Exception e) {
            logger.error("微信接口获取用户信息异常:{}", e);
            e.printStackTrace();
        }
        return userInfoMap;
    }

    /**
     * 创建公众号菜单
     * @throws IOException
     *             创建Menu
     * @Title: createMenu
     * @Description: 创建Menu
     * @param @return
     * @param @throws IOException 设定文件
     * @return int 返回类型
     * @throws
     */
    public static String createMenu(String  menu,String appid,String secret) throws IOException {
        // 此处改为自己想要的结构体，替换即可
        String access_token = getAccess_token(appid,secret);
        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
                + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(menu.getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            logger.info("创建公众号菜单接口返回:{}"+message);
            return "返回信息" + message;
        } catch (MalformedURLException e) {
            logger.error("创建公众号菜单异常:{}",e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("创建公众号菜单异常:{}",e);
            e.printStackTrace();
        }
        logger.warn("createMenu 失败");
        return "createMenu 失败";
    }

    /**
     * 删除公众号菜单
     * @throws IOException
     *             删除当前Menu
     * @Title: deleteMenu
     * @Description: 删除当前Menu
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String deleteMenu(String appid,String secret) throws IOException {
        String access_token = getAccess_token(appid,secret);
        String action = "https://api.weixin.qq.com/cgi-bin/menu/delete? access_token="
                + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            OutputStream os = http.getOutputStream();
            os.flush();
            os.close();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            logger.warn("删除公众号菜单接口返回信息:{}", message);
            return "deleteMenu返回信息:" + message;
        } catch (MalformedURLException e) {
            logger.error("删除公众号菜单接口异常:{}", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("删除公众号菜单接口异常:{}", e);
            e.printStackTrace();
        }
        logger.warn("deleteMenu 失败");
        return "deleteMenu 失败";
    }

    // 获取授权请求
    public static String getCodeRequest(String redirectUri, String scope,
                                        String appid,String  state) {
        String result = GetCodeRequest;

        result = result.replace("APPID", urlEnodeUTF8(appid));

        result = result.replace("REDIRECT_URI",
                urlEnodeUTF8(redirectUri));

        result = result.replace("SCOPE", scope);
        result = result.replace("STATE", state);

        return result;

    }

    /**
     * 初始化微信自定义分享数据
     * @param request
     * @param appid
     * @param secret
     * @param systemUrl
     */
    public static   void   initWxShare(HttpServletRequest request, String appid, String secret,String systemUrl){
        //初始化JSSDK需要的东西
        String  url = "";
        if (null == request.getQueryString()
                || "".equals(request.getQueryString())) {
            url = request.getRequestURL().toString();
        } else {
            url = request.getRequestURL() + "?" + request.getQueryString();
        }
        Map<String, String> jssdkInfoMap = WechatUtil.getJssdkinfo(url, appid, secret);
        request.setAttribute("timestamp", jssdkInfoMap.get("timestamp"));
        request.setAttribute("nonce", jssdkInfoMap.get("nonce"));
        request.setAttribute("getSHA1", jssdkInfoMap.get("getSHA1"));
        request.setAttribute("appid", jssdkInfoMap.get("appid"));
        request.setAttribute("systemUrl",systemUrl);
    }
    // 进行转码

    public static String urlEnodeUTF8(String str) {

        String result = str;

        try {

            result = URLEncoder.encode(str, "UTF-8");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return result;

    }
}
