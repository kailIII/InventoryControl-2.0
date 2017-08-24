package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.server.UnicastRemoteObject;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import com.candlelabs.inventory.rmi.interfaces.service.MessageResponder;
import com.candlelabs.inventory.rmi.interfaces.service.ServerResponder;

/**
 *
 * @author VakSF
 */
public class CallbackServerImpl extends UnicastRemoteObject
     implements ServerResponder {
    
    private static ServerResponder server;
    
    private final String serverName;

    private final Hashtable<String, PermissionMessageResponder> nameReceiver = new Hashtable<>();

    public CallbackServerImpl(String serverName) throws RemoteException {
        
        super();
        
        this.serverName = serverName;
        
        System.out.println("New Callback Server");
    }

    @Override
    public void register(MessageResponder receiver) throws RemoteException {
        
        int i = 0;
        while (containsName(receiver.getName())) {
            receiver.renameIfExist(receiver.getName() + i);
            i++;
        }
        
        this.nameReceiver.put(receiver.getName(),
                new PermissionMessageResponder(this, receiver));
        
        receiver.sendMessageToClient("Connected \nWelcome to THM-IRC-Network - " + this.serverName);
        
        sendMessage(receiver, "Connected");
    }
    
    public boolean containsName(String name) {
        return nameReceiver.containsKey(name);
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
        
        sender.sendMessageToClient("Command not found");
    }

    @Override
    public void quit() throws RemoteException {
        
        String message = "Server CLosed";
        
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
