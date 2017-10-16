package com.szachnowicz.interfaceRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRegistry extends Remote {
	
	int registerObject(Object o) throws RemoteException;
	
	boolean unregisterObject(int number) throws RemoteException;
	
	Object getManager() throws RemoteException;
	
	List<Object> getBilboards() throws RemoteException;
}
