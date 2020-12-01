package com.liveme.demo.bytes;

import com.liveme.demo.util.Md5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.CRC32;

public class ByteTest {


    public static void main(String[] args) {

//        String str = "aaaaaaa\n";
//
//        System.out.println(str.substring(0,str.length()-"\n".length()));

//        for (int i=0i<) {
//
//        }

//        System.out.println(getHashCode("16028502027475869999"));

//        Calendar instance = Calendar.getInstance(Locale.CHINA);
//        Date timeOfChina = instance.getTime();
//        System.out.println(timeOfChina);
//
        byte c = -1;
        int a = -1;

//        System.out.println(c==a);

//        System.out.println(getHashCode("1324183562664157185"));

//        System.out.println("sign:"+sign());
        System.out.println(calculateSign("","1","1","1"));


        String userId = "";

        System.out.println(41%5);



    }


    /**
     * 根据dataId获取hashCode
     *
     * @param dataId
     * @return
     */
    public static int getHashCode(String dataId) {
        CRC32 crc32 = new CRC32();
        crc32.update(dataId.getBytes());
        int hashCode = (int) crc32.getValue();
        return hashCode;
    }


    /**
     * sign 签名算法
     */
    public static String sign() {


        //nonce 随机字符串
        String nonce = UUID.randomUUID().toString().replace("-","");
        System.out.println("nonce ：" + nonce);
        //appKey
        String appKey = "08f34322";
        System.out.println("appKey:"+appKey);
        //时间戳
        String timestamps = "1604647753";
        System.out.println("timestamps : "+timestamps);
        //appId
        String appID = "tvbs";
        return calculateSign(appID,timestamps,nonce,appKey);
    }

    private static String getSignature(){

        String appSecret = "306aa0883e2bce6b5301d773e061642b";
        System.out.println("appSecret:"+appSecret);
        //时间戳
        String timestamps = "1604647753";
        System.out.println("timestamps:"+timestamps);
        //nonce 随机字符串
        String nonce = UUID.randomUUID().toString();
        System.out.println("nonce ：" + nonce);
        String sortStr = timestamps+nonce+appSecret;
        return Md5Util.md5(sortStr);

    }



    private static String calculateSign(String appId, String timev, String noncev, String appKey) {
        String time = appId + "|" + appKey + "|" + timev + "|" + noncev;
        System.out.println("cacule:" + time);
        return DigestUtils.shaHex("bxfmRLuHAtCJVenaykqjwseyVqSRyXPn|6b07ecbd|1605862349|12345678").toUpperCase();
    }









}
