package RMI;

import com.szachnowicz.impl.ManagerImpl;
import com.szachnowicz.interfaceRmi.IRegistry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ManagerMain {

    public static void main(String[] args) {

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IRegistry registry = (IRegistry) rmiRegistry.lookup("Registry");

            ManagerImpl manager = new ManagerImpl(registry);
            int number = registry.registerObject(manager);
            System.out.println("MANAGER: Manager registered with number " + number);


//            manager.createAndShowGUI();
        } catch (RemoteException e) {
            System.err.println("MANAGER: Manager error:\n");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("MANAGER: Cannot find registry!:\n");
            e.printStackTrace();
        }

    }
}
