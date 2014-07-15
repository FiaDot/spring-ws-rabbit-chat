package com.fullstackstarter.wschat.chat;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;



public class User {
	private final int id;
	private final WebSocketSession session;
	
	
	public User(int id, WebSocketSession session) {
		this.id = id;
		this.session = session;		
	}
	
    protected void sendMessage(String msg) throws Exception {
    	//if (!session.isOpen() )
    	//	return;
    	
    	session.sendMessage(new TextMessage(msg));
    }
    
    public int getId() {
        return id;
    }


}