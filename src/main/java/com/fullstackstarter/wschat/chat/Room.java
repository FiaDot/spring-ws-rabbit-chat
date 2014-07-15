package com.fullstackstarter.wschat.chat;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;


public class Room {
	private static final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<Integer, User>();
	
    public static synchronized void addUser(User user) {        
        users.put(Integer.valueOf(user.getId()), user);
    }
    

    public static synchronized void removeUser(User user) {
    	users.remove(Integer.valueOf(user.getId()));      
    }
    
    public static void OnMessage(String message) throws Exception {    	       
        broadcast(message);
    }
    
    public static Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users.values());
    }

    public static void broadcast(String message) throws Exception {
        for (User user : Room.getUsers()) {        
        	user.sendMessage(message);
        }
    }

}