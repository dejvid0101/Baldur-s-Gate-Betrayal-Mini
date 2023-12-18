package me.projects.baldur.betrayal_at_baldurs_gate.InGameChat;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class InGameChatImpl implements InGameChatService{
    public InGameChatImpl() {
        msgs = new ArrayList<>();
    }

    private List<String> msgs;
    @Override
    public void sendMsg(String chatMsg) throws RemoteException {
msgs.add(chatMsg);
    }

    @Override
    public List<String> getAllMsgs() throws RemoteException {
        return msgs;
    }
}
