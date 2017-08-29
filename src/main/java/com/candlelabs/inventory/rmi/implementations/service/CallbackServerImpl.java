package com.candlelabs.inventory.rmi.implementations.service;

import com.candlelabs.inventory.controller.mastermind.MastermindController;
import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.model.Supplier;
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
    public boolean register(MessageResponder receiver) throws RemoteException {
        
        if (!this.nameReceiver.containsKey(receiver.getName())) {

            this.nameReceiver.put(
                    
                    receiver.getName(), 
                    
                    new PermissionMessageResponder(
                            this, receiver
                    )
            );
            
            this.sendMessage(receiver, "broadcast");
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean unregister(MessageResponder receiver) throws RemoteException {
        
        if (nameReceiver.containsKey(receiver.getName())) {
            
            sendMessage(receiver, "Disconnected");
            
            nameReceiver.remove(receiver.getName());
            
            return true;
        }
        
        return false;
        
    }

    @Override
    public void productAction(
            MessageResponder sender, Product product, String action, int index) throws RemoteException {
        
        this.nameReceiver.values().forEach((PermissionMessageResponder receiver) -> {
            
            try {
                
                MessageResponder messageResponder = receiver.getMessageResponder();
                
                if (!messageResponder.getName().equals(sender.getName())) {
                    
                    messageResponder.productAction(product, action, index);
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        });
        
    }
    
    @Override
    public void categoryAction(
            MessageResponder sender, Category category, String action, int index) throws RemoteException {
        
        this.nameReceiver.values().forEach((PermissionMessageResponder receiver) -> {
            
            try {
                
                MessageResponder messageResponder = receiver.getMessageResponder();
                
                if (!messageResponder.getName().equals(sender.getName())) {
                    
                    messageResponder.categoryAction(category, action, index);
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        });
        
    }
    
    @Override
    public void measurementAction(
            MessageResponder sender, Measurement measurement, String action, int index) throws RemoteException {
        
        this.nameReceiver.values().forEach((PermissionMessageResponder receiver) -> {
            
            try {
                
                MessageResponder messageResponder = receiver.getMessageResponder();
                
                if (!messageResponder.getName().equals(sender.getName())) {
                    
                    messageResponder.measurementAction(measurement, action, index);
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        });
        
    }
    
    @Override
    public void supplierAction(
            MessageResponder sender, Supplier supplier, String action, int index) throws RemoteException {
        
        this.nameReceiver.values().forEach((PermissionMessageResponder receiver) -> {
            
            try {
                
                MessageResponder messageResponder = receiver.getMessageResponder();
                
                if (!messageResponder.getName().equals(sender.getName())) {
                    
                    messageResponder.supplierAction(supplier, action, index);
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        });
        
    }
    
    @Override
    public void sendMessage(MessageResponder sender, String message) throws RemoteException {
        
        this.nameReceiver.values().forEach((PermissionMessageResponder receiver) -> {
            
            try {
                
                receiver.getMessageResponder().sendMessageToClient(message);
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
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
        
        Hashtable<String, MessageResponder> users = new Hashtable<>();
        
        this.nameReceiver.keySet().forEach((name) -> {
            users.put(name, this.nameReceiver.get(name).getMessageResponder());
        });
        
        return users;
        
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
        
        Collection<MessageResponder> resp = new HashSet<>();
        
        nameReceiver.values().forEach((perm) -> {
            resp.add(perm.getMessageResponder());
        });
        
        return resp;
    }
    
}
