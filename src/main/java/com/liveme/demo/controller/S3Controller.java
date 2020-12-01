package com.liveme.demo.controller;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.liveme.demo.util.AmazonUtil;
import com.liveme.demo.util.TimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/s3")
public class S3Controller {


    private static final String bucketName = "liveme-im-static";


    private static final String CDNHost = "http://mesx.linkv.io/";   //http://mesx.ksmobile.net/

    private static final String CDNOnlyHost = "mesx.linkv.io";       //mesx.ksmobile.net


    @RequestMapping("/getToken")
    public Map<String,String> geTtoken(){
        //获取token
        Map<String, String> map = getToken("liveme", "im", "jpeg", "xuliang", "jpeg");

        System.out.println(map);
        return map;
    }


    /**
     * 获取上传token
     */
    public static Map<String, String> getToken(String appId, String fService, String fSuffix, String user, String fType) {

        Map<String, String> map = new HashMap<>();
//        AmazonS3 s3client = AmazonS3Client.builder()
//                .withRegion("us-east-1")
//                .withPathStyleAccessEnabled(true)
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
//                .build();


        AmazonS3 s3client = AmazonUtil.getInstance().getS3client();

        try {
            String objectKey = getFilePath(appId, fService, fSuffix, user, fType);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey);
            generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
            long ful_expire = System.currentTimeMillis() + 60 * 60 * 1000;
            generatePresignedUrlRequest.setExpiration(new Date(ful_expire));
            generatePresignedUrlRequest.addRequestParameter("x-amz-acl", "public-read");

            URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
            String uploadUrl = changeURL(url);
            String loadUrl = CDNHost + objectKey;
            URL geturl = new URL(loadUrl);
            map.put("upload", uploadUrl);
            map.put("get", geturl.toString());
        } catch (Exception e) {

            e.printStackTrace();

        }

        return map;

    }


    public static String changeURL(URL url) {
        String ful = url.toString();
        ful = ful.replace(url.getHost(), CDNOnlyHost);
        //因为安卓端上有BUG，修改为HTTP的时候，引起安卓端崩溃，老版本不能够支持HTTPS，新版本修改：2019-04-17，留待以后放开
        //		ful = ful.replace("https://", "http://");
        return ful;
    }

    public static String getFilePath(String appId, String fservice, String fSuffix, String user, String fType) {
        String data = TimeFormat.getFormatDate(new Date(), TimeFormat.YYYYMMDD);
        String time = TimeFormat.getFormatDate(new Date(), "HHmm");
        StringBuilder builder = new StringBuilder();
        builder.append(appId);
        builder.append("/");
        builder.append(fservice);
        builder.append("/");
        builder.append(fType);
        builder.append("/");

        builder.append(data);
        builder.append("/");
        builder.append(time);
        builder.append("/");
        builder.append(user + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().replaceAll("-", "")
                + "." + fSuffix);
        String bucketName = builder.toString();
        return bucketName;
    }


}
