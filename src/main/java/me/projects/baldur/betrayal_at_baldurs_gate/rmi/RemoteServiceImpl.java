package me.projects.baldur.betrayal_at_baldurs_gate.rmi;

import java.rmi.RemoteException;

public class RemoteServiceImpl implements RemoteService {
    @Override
    public int countConsonants(String sentence) throws RemoteException {
        return sentence
                .toLowerCase()
                .replaceAll("[^a-z]", "") // remove all non letters
                .replaceAll("[aeiou]", "") // remove vowels
                .length();
    }

    @Override
    public String swapCharacters(String sentence, char oldChar, char newChar) throws RemoteException {
        return sentence.replace(oldChar, newChar);
    }
}
