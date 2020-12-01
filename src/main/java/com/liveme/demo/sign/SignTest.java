package com.liveme.demo.sign;

import com.liveme.demo.util.MD5;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SignTest {




//
//	ken
//	signature 生成方法
//	 将 timestamp, appSecret, nonce三个参数的值按字母排序后，相连，并用md5算法加密作为signature    appSecret由之前createAppKeySecret接口获取
//	 附python生成校验码方法
//	 import hashlib
//	 sign = "".join(sorted([secret, str(nonce), str(timestamp)]))
//	 md5_sign = hashlib.md5(sign).hexdigest()
//	 获取token成功后 token同时存储于 redis 10.66.108.92:6379  key-value  key格式 t_{appKey}_{userId} 如 t_8ebb97a9_123

    public static void getLoginToken() {

        String appSecret="cf5cd2f23cd7f40b71afe9fe90bea4fc";
        String appKey="961a2b83";


        int secondTimeStamp=(int) (System.currentTimeMillis() / 1000);
        String randomNoce=new Random().nextInt(99999999)+"";

        String userId="asdf";


        List<String> signKeyArray=new ArrayList<String>();

        signKeyArray.add(randomNoce);
        signKeyArray.add(appSecret);
        signKeyArray.add(""+secondTimeStamp);
        for(String data:signKeyArray) {
            System.out.println("排序前："+data);
        }
        Collections.sort(signKeyArray);
        String signKeyStr="";
        for(String signParam:signKeyArray) {
            System.out.println("排序后："+signParam);
            signKeyStr=signKeyStr+signParam;
        }
        System.out.println("MD5Before:"+signKeyStr);
        String signKeyMd5Str= MD5.crypt(signKeyStr);

        System.out.println("randomNoce:"+randomNoce +" appSecret:"+appSecret+" secondTimeStamp:"+secondTimeStamp);
        System.out.println("signKeyMd5Str:"+signKeyMd5Str);

        Map<String,String> headerMap=new HashMap<String,String>();
        headerMap.put("signature", signKeyMd5Str);
        headerMap.put("appkey", appKey);
        headerMap.put("timestamp", secondTimeStamp+"");
        headerMap.put("nonce", randomNoce);
        NameValuePair userIdParam=new BasicNameValuePair("userId",userId);
        List<NameValuePair> paramList=new ArrayList<NameValuePair>();
        paramList.add(userIdParam);



    }


    public static void main(String[] args) {



        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(new Date(1558184646827l)));
        getLoginToken();

    }


}
