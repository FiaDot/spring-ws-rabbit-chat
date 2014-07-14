package com.fullstackstarter.wschat.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageQueueHandler {

	private static final Logger logger = LoggerFactory.getLogger(MessageQueueHandler.class);

	public void listen(String foo) {
		logger.info(foo);		
	}
}