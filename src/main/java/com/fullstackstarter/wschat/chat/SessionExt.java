package com.fullstackstarter.wschat.chat;




public class SessionExt {
	private boolean init;
	private long uid;
	private String id;
	
	public boolean isInit() {
		return init;
	}
	public void setInit(boolean init) {
		this.init = init;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}