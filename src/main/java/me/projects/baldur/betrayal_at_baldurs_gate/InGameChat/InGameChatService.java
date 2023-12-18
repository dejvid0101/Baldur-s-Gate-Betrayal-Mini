package me.projects.baldur.betrayal_at_baldurs_gate.InGameChat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InGameChatService extends Remote {
    String CHAT_OBJECT_NAME="package me.projects.baldur.betrayal_at_baldurs_gate.InGameChat.InGameChatService";

    void sendMsg(String chatMsg) throws RemoteException;

    List<String> getAllMsgs() throws RemoteException;

}
