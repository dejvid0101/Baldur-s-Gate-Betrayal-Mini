package me.projects.baldur.betrayal_at_baldurs_gate.rmi;

import me.projects.baldur.betrayal_at_baldurs_gate.classes.NetworkConfig;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {



    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(NetworkConfig.RMI_PORT);
            RemoteService remoteService = new RemoteServiceImpl();
            RemoteService skeleton = (RemoteService) UnicastRemoteObject.exportObject(remoteService, NetworkConfig.RANDOM_PORT_HINT);
            registry.rebind(RemoteService.REMOTE_OBJECT_NAME, skeleton);
            System.err.println("Object registered in RMI registry");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
