package com.candlelabs.inventory.rmi.implementations.service;

import com.candlelabs.inventory.controller.category.CategoryController;
import com.candlelabs.inventory.controller.mastermind.MastermindController;
import com.candlelabs.inventory.controller.product.ProductController;
import com.candlelabs.inventory.controller.store.StoreController;
import com.candlelabs.inventory.model.Category;

import com.candlelabs.inventory.rmi.interfaces.service.MessageResponder;
import com.candlelabs.inventory.rmi.interfaces.service.ServerResponder;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;

/**
 *
 * @author VakSF
 */
public class CallbackClientImpl extends UnicastRemoteObject
        implements MessageResponder {
    
    private String name;
    private ServerResponder server;
    
    private MastermindController mastermindController;
    private StoreController storeController;
    
    public CallbackClientImpl(ServerResponder serverResponder, String name)
            throws RemoteException {
        
        super();
        
        this.server = serverResponder;
        this.name = name;
    }
    
    
    public void initMastermind(MastermindController controller) {
        this.mastermindController = controller;
    }
    
    public void initStore(StoreController controller) {
        this.storeController = controller;
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
    
    @Override
    public void categoryAction(Category category, String action, int index) throws RemoteException {
        
        if (mastermindController != null) {
            
            ProductController productCtrl =
                    mastermindController
                            .getProductController();
            
            CategoryController categoryCtrl =
                    mastermindController
                            .getProductController()
                            .getCategoryController();
            
            if (action.equalsIgnoreCase("create")) {
                
                productCtrl
                        .getCategories()
                        .add(category);
                
                categoryCtrl
                        .getCategories()
                        .add(category);
                
            } else {
                
                if (action.equalsIgnoreCase("edit")) {
                    
                    productCtrl
                            .getCategoryCB()
                            .getItems()
                            .setAll(
                                    productCtrl.getProductService()
                                            .listCategories()
                            );
                    
                    Category editCategory = categoryCtrl
                            .getCategories()
                            .get(index);
                    
                    editCategory.setName(category.getName());
                    
                    categoryCtrl.getCategoriesTV().refresh();
                    
                } else {
                    
                    if (action.equalsIgnoreCase("delete")) {
                        
                        Platform.runLater(() -> {
                            
                            productCtrl
                                    .getCategories()
                                    .remove(category);
                            
                            categoryCtrl
                                    .getCategories()
                                    .remove(category);
                            
                        });
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
}
