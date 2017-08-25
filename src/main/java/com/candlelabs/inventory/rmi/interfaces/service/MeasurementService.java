package com.candlelabs.inventory.rmi.interfaces.service;

import com.candlelabs.inventory.model.Measurement;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Arturh
 */
public interface MeasurementService extends Remote {
    
    public Measurement readMeasurement(Integer measurementId) throws RemoteException;
    public Integer createMeasurement(Measurement measurement) throws RemoteException;
    public boolean updateMeasurement(Measurement measurement) throws RemoteException;
    public boolean deleteMeasurement(Measurement measurement) throws RemoteException;
    public List<Measurement> listMeasurements() throws RemoteException;
    
}
