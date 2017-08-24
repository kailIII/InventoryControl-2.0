package com.candlelabs.inventory.rmi.implementations.service;

import com.candlelabs.inventory.rmi.interfaces.service.MessageResponder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author VakSF
 */
public class PermissionMessageResponder {
    
    private MessageResponder user;
    private CallbackServerImpl server;
    private String displayName;
    
    private final List<String> permissions;
    
    public PermissionMessageResponder(CallbackServerImpl server, MessageResponder user) {
        
        this.user = user;
        this.server = server;
        
        try {
            
            this.displayName = this.user.getName();
            
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.toString());
        }
        
        this.permissions = new ArrayList<>();
    }
    
    public void sendMessage(String message) throws RemoteException{
        this.user.sendMessageToClient(message);
    }
    
    public MessageResponder getMessageResponder() {
        return user;
    }
    
    public boolean addPermission(String permission) {
        
        if (!permissions.contains(permission)) {
            
            permissions.add(permission);
            
            return true;
        }
        
        return false;
    }
    
    public boolean removePermission(String permission) {
        
        if (permissions.contains(permission)) {
            
            permissions.remove(permission);
            
            return true;
        }
        
        return false;
    }
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    public CallbackServerImpl getServer() {
        return server;
    }
    
    public void disconnect(String... message) throws RemoteException {
        
        if (message == null) {
            
            String kickMessage = "Du wurdest von einem Moderator gekickt!";
            
            System.out.println(kickMessage);
            
            this.user.sendMessageToClient(kickMessage);
            
        } else {
            
            this.user.sendMessageToClient(Arrays.toString(message));
            
        }
        
        this.server.unregister(this.getMessageResponder());
        
        this.user.exit();
    }
    
    public String getChatName() {
        return this.displayName;
    }
    
    public void setChatName(String chatName){
        this.displayName=chatName;
    }
}