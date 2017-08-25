package com.candlelabs.inventory.rmi.implementations.service;

import com.candlelabs.inventory.controller.interfaces.MastermindInitializer;
import com.candlelabs.inventory.controller.mastermind.MastermindController;
import com.candlelabs.inventory.rmi.interfaces.service.MessageResponder;
import com.candlelabs.inventory.rmi.interfaces.service.ServerResponder;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author VakSF
 */
public class CallbackClientImpl extends UnicastRemoteObject
     implements MessageResponder, MastermindInitializer {
    
    private String name;
    private ServerResponder server;
    
    private MastermindController mastermindController;
    
    public CallbackClientImpl(ServerResponder serverResponder, String name) throws RemoteException {
        
        super();
        
        this.name = name;
        
        this.server = serverResponder;
    }
    
    @Override
    public void init(MastermindController controller) {
        this.mastermindController = controller;
    }
    
    public boolean register() throws RemoteException {
        return this.server.register(this);
    }
    
    public boolean unregister() throws RemoteException {
        return this.server.unregister(this);
    }

    public ServerResponder getServer() {
        return server;
    }

    @Override
    public void sendMessageToClient(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
    }
    
    @Override
    public void exit() throws RemoteException {
        if (!server.isOnline(this))
            System.exit(0);
    }
    
}
