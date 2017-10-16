package RMI;

import com.szachnowicz.impl.BilboardImpl;
import com.szachnowicz.interfaceRmi.IBillboard;
import com.szachnowicz.interfaceRmi.IRegistry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BilboardMain {
    public static void main(String[] args) {

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IRegistry registry = (IRegistry) rmiRegistry.lookup("Registry");

            IBillboard billboard = new BilboardImpl(registry);
            registry.registerObject(billboard);
//            IManager manager = (IManager) registry.getManager();
////            manager.bindBillboard(billboard);

        } catch (RemoteException e) {
            System.err.println("BILBOARD: bilboard error:\n");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("BILBOARD: Cannot find registry!:\n");
            e.printStackTrace();
        }


    }

}
