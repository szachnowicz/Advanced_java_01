package com.szachnowicz.impl;

import com.szachnowicz.enitiy.Order;
import com.szachnowicz.interfaceRmi.IBillboard;
import com.szachnowicz.interfaceRmi.IManager;
import com.szachnowicz.interfaceRmi.IRegistry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ManagerImpl extends UnicastRemoteObject implements IManager {
    private List<IBillboard> bilboardList;
    private IRegistry registry;

    public ManagerImpl(IRegistry registry) throws RemoteException {
        super();
        this.registry = registry;
        this.bilboardList = (List<IBillboard>) (List<?>) registry.getBilboards();
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        if (bilboardList.contains(billboard)) {
            System.out.println("Bilboard allready binded");
            return -1;
        }
        bilboardList.add(billboard);
        System.out.println("Bilboard binded");
        return 1;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        System.out.println("place order");
        this.bilboardList = (List<IBillboard>) (List<?>) registry.getBilboards();
        if (bilboardList != null) {
            for (IBillboard billboard : bilboardList) {
                billboard.addAdvertisement(order.getAdvertText(), order.getDisplayPeriod(), 0);
            }
        }
        return true;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        return false;
    }

}
