package com.liveme.demo.util;

import com.mongodb.ServerAddress;

public class ServerAddressExtends extends ServerAddress {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dbName;
	private String userName;
	private String pwd;
	private ServerAddress serverAddress;

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public ServerAddress getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(ServerAddress serverAddress) {
		this.serverAddress = serverAddress;
	}

	@Override
	public String toString() {
		return "ServerAddressExtends [dbName=" + dbName + ", userName=" + userName + ", pwd=" +  " " + ", serverAddress=" + serverAddress + "]";
	}

}
