package com.liveme.demo.util;

import com.amazonaws.auth.EC2ContainerCredentialsProviderWrapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class AmazonUtil {
	
	private AmazonS3 s3client;
	
	private static final class AmazonUtilHold{
		private static final AmazonUtil instance = new AmazonUtil();
	}
	
	private AmazonUtil(){
		AmazonS3 s3client = AmazonS3Client.builder()
				.withRegion("us-east-1")
//				.withPathStyleAccessEnabled(false)
//				.withCredentials(new ProfileCredentialsProvider())
				.withCredentials(new EC2ContainerCredentialsProviderWrapper())
				.build();
		setS3client(s3client);
	}
	
	public static AmazonUtil getInstance(){
		return AmazonUtilHold.instance;
	}

	public AmazonS3 getS3client() {
		return s3client;
	}

	public void setS3client(AmazonS3 s3client) {
		this.s3client = s3client;
	}

}
