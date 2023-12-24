package me.projects.baldur.betrayal_at_baldurs_gate.jndi;

import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class InitialDirContextCloseable extends InitialDirContext implements AutoCloseable {
    public InitialDirContextCloseable(Hashtable<?, ?> environment) throws NamingException {
        super(environment);
    }

}

