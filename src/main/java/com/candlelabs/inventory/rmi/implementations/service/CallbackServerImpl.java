package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.server.UnicastRemoteObject;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import com.candlelabs.inventory.rmi.interfaces.service.MessageResponder;
import com.candlelabs.inventory.rmi.interfaces.service.ServerResponder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author VakSF
 */
public class CallbackServerImpl extends UnicastRemoteObject
     implements ServerResponder {
    
    private static ServerResponder server;
    
    private final String serverName;

    private final Map<String, PermissionMessageResponder> nameReceiver;

    public CallbackServerImpl(String serverName) throws RemoteException {
        
        super();
        
        this.nameReceiver = new HashMap<>();
        
        this.serverName = serverName;
        
    }
    
    @Override
    public void register(MessageResponder receiver) throws RemoteException {
        
        int i = 0;
        
        while (this.nameReceiver.containsKey(receiver.getName())) {
            receiver.setName(receiver.getName() + "_" + i);
            i++;
        }
        
        System.out.println(receiver.getName());
        
        this.nameReceiver.put(receiver.getName(), new PermissionMessageResponder(this, receiver));
        
        receiver.sendMessageToClient("Connected - Welcome to CandleLabs - " + this.serverName);
        receiver.sendMessageToClient("You're " + receiver.getName());
        
    }
    
    @Override
    public void unregister(MessageResponder receiver) throws RemoteException {
        
        if (nameReceiver.containsKey(receiver.getName())) {
            
            sendMessage(receiver, "Disconnected");
            
            nameReceiver.remove(receiver.getName());
        }
        
    }
    
    @Override
    public void sendMessage(MessageResponder sender, String message) throws RemoteException {
        
        this.nameReceiver.values().forEach((PermissionMessageResponder receiver) -> {
            
            try {
                
                receiver.getMessageResponder().sendMessageToClient(message);
                
            } catch (RemoteException ex) {
                
            }
            
        });
        
    }

    @Override
    public void quit() throws RemoteException {
        
        String message = "Server Closed";
        
        for (PermissionMessageResponder connectedUser : nameReceiver.values()) {
            connectedUser.disconnect(message);
        }
        
        System.exit(0);
    }

    @Override
    public Hashtable<String, MessageResponder> getUsers() throws RemoteException {
        
        Hashtable<String, MessageResponder> tempRes = new Hashtable<>();
        
        for (String name : this.nameReceiver.keySet()) {
            tempRes.put(name, this.nameReceiver.get(name).getMessageResponder());
        }
        
        return tempRes;
        
    }

    @Override
    public boolean isOnline(MessageResponder receiver) throws RemoteException {
        return isOnline(receiver.getName());
    }

    @Override
    public boolean isOnline(String receiver) throws RemoteException {
        return nameReceiver.containsKey(receiver);
    }
    
    @Override
    public Collection<MessageResponder> getUser() throws RemoteException {
        
        Collection<MessageResponder> resp = new HashSet<MessageResponder>();
        
        for (PermissionMessageResponder perm : nameReceiver.values()) {
            resp.add(perm.getMessageResponder());
        }
        
        return resp;
    }
    
    
    
}
