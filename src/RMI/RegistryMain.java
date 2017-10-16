package RMI;

import com.szachnowicz.impl.RegistryImpl;
import com.szachnowicz.interfaceRmi.IRegistry;
;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryMain {
    public static void main(String[] args) {
        try {

     Registry rmiRegistry = LocateRegistry.createRegistry(1099);
            IRegistry myRegistryImpl = new RegistryImpl(rmiRegistry);
            System.out.println("RMI registry started:" + rmiRegistry.toString());

            rmiRegistry.bind("Registry", myRegistryImpl);
            System.out.println("Registry server ready");

        } catch (RemoteException e) {
            System.err.println("Registry server error:\n");
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            System.err.println("Registry server error:\n");
            e.printStackTrace();
        }
    }
}
