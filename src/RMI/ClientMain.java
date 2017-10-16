package RMI;

import com.szachnowicz.enitiy.Order;
import com.szachnowicz.impl.BilboardImpl;
import com.szachnowicz.impl.ClientImpl;
import com.szachnowicz.interfaceRmi.IBillboard;
import com.szachnowicz.interfaceRmi.IManager;
import com.szachnowicz.interfaceRmi.IRegistry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;

public class ClientMain {

    public static void main(String[] args) {

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IRegistry registry = (IRegistry) rmiRegistry.lookup("Registry");

            IManager manager = (IManager) registry.getManager();

            ClientImpl client = new ClientImpl(manager);

            client.setOrderId(1);

        } catch (RemoteException e) {
            System.err.println("Client: Client error:\n");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("Client: Cannot find registry!:\n");
            e.printStackTrace();
        }


    }
}
