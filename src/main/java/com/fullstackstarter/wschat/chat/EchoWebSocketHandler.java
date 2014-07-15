package com.fullstackstarter.wschat.chat;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class EchoWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger =  (Logger) LoggerFactory.getLogger(EchoWebSocketHandler.class);
/*	
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payloadMessage = (String) message.getPayload();
    	logger.info("msg = " + payloadMessage);

        session.sendMessage(new TextMessage("ECHO : " + payloadMessage));
    }
*/   
	

	// private ConcurrentHashMap<WebSocketSession, SessionExt>	sessions = new ConcurrentHashMap<WebSocketSession, SessionExt>(); 
	
	private static final AtomicInteger next_id = new AtomicInteger(0);
	
	private final int id;
	private User user;

	    
    public EchoWebSocketHandler() {
        this.id = next_id.getAndIncrement();
    }
    
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");

		if ( 512 != message.getPayload().length() || false == message.isLast() ) {
			logger.error("Payload fragmentation size="+message.getPayload().length() + " isLast=" + message.isLast());
		}
			
		try {
			Room.OnMessage(message.getPayload());
		}
		catch (Throwable e){
			// logger.error("err broadcasting=" + e.getMessage());
		}
		// logger.info("msg = " + message.getPayload());

		/*
		Iterator<WebSocketSession> it = sessions.keySet().iterator();
		
		while(it.hasNext()) {
			WebSocketSession sessionDest = it.next();
			
			// if (sessionDest.equals(session)) {
			//				continue;
			// }
 
			if ( false == sessionDest.isOpen() ) { 
				continue;
			}
			
			sessionDest.sendMessage(new TextMessage(message.getPayload()));			
		}
		*/

		// session.sendMessage(returnMessage);
	}
	
	
	 
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Connection이 구성된 후, 호출되는 method
        super.afterConnectionEstablished(session);
                
        this.user = new User(id, session);
        Room.addUser(user);
        /*
        SessionExt ext = new SessionExt();
        sessions.put(session, ext);
        */
        logger.info("Conn session=" + session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Connection이 종료된 후, 호출되는 method
        super.afterConnectionClosed(session, status);
        
        logger.info("Diss session=" + session);
        
        // sessions.remove(session);
        
        Room.removeUser(user);
        
    }
}

