package com.szachnowicz.impl;

import com.szachnowicz.interfaceRmi.IBillboard;
import com.szachnowicz.interfaceRmi.IManager;
import com.szachnowicz.interfaceRmi.IRegistry;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RegistryImpl extends UnicastRemoteObject implements IRegistry {


    private IManager manager;
    private int managerId;
    private Registry registry;
    private Map<Integer, IBillboard> bilboardsMap;

    public RegistryImpl(Registry rmiRegistry) throws RemoteException {
        super();
        bilboardsMap = new LinkedHashMap<>();
        registry = rmiRegistry;
    }

    @Override
    public int registerObject(Object o) throws RemoteException {
        boolean resultOk = false;

        int id = getUniqueId();
        if (IBillboard.class.isInstance(o)) {
            bilboardsMap.put(id, (IBillboard) o);
            resultOk = true;
            System.out.println("BILBOARD registered");
        }
        if (IManager.class.isInstance(o)) {
            manager = (IManager) o;
            managerId = id;
            System.out.println("MANAGER registered");
            resultOk = true;
        }
        return resultOk ? id : -1;
    }

    private Integer getUniqueId() {
        Integer gerateID;
        Random random = new Random();
        boolean inMap;
        do {
            gerateID = random.nextInt(999);
            inMap = false;

            for (Map.Entry<Integer, IBillboard> worker : bilboardsMap.entrySet()) {
                if (managerId == gerateID.intValue() || worker.getKey().intValue() == gerateID.intValue()) {
                    inMap = true;
                    break;
                }
            }
        } while (inMap);

        return gerateID;
    }


    @Override
    public boolean unregisterObject(int number) throws RemoteException {
        boolean removedObject = false;
        if (number == managerId) {
            manager = null;
            removedObject = true;
        }

        return false;
    }

    @Override
    public Object getManager() throws RemoteException {
        return manager;
    }

    @Override
    public List<Object> getBilboards() throws RemoteException {
        List<Object> objectList = new ArrayList<>();
        objectList.addAll(bilboardsMap.values());
        return objectList;
    }
}
