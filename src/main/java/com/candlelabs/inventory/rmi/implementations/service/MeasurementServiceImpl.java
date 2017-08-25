package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.persistence.dao.MeasurementDao;
import com.candlelabs.inventory.rmi.interfaces.service.MeasurementService;

/**
 *
 * @author VakSF
 */
public class MeasurementServiceImpl extends UnicastRemoteObject implements MeasurementService {
    
    private final MeasurementDao measurement = new MeasurementDao();
    
    public MeasurementServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Integer createMeasurement(Measurement measurement) throws RemoteException {
        return this.measurement.create(measurement);
    }

    @Override
    public Measurement readMeasurement(Integer measurementId) throws RemoteException {
        return this.measurement.read(measurementId);
    }

    @Override
    public boolean updateMeasurement(Measurement measurement) throws RemoteException {
        return this.measurement.update(measurement);
    }

    @Override
    public boolean deleteMeasurement(Measurement measurement) throws RemoteException {
        return this.measurement.delete(measurement);
    }

    @Override
    public List<Measurement> listMeasurements() throws RemoteException {
        return this.measurement.readAll();
   }
    
}
