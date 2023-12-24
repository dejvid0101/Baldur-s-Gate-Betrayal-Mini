package me.projects.baldur.betrayal_at_baldurs_gate.classes;

import me.projects.baldur.betrayal_at_baldurs_gate.jndi.InitialDirContextCloseable;

import javax.naming.Context;
import javax.naming.NamingException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.util.Hashtable;
import java.util.Properties;

public class ConfigurationReader {

    private static ConfigurationReader reader;

    private Hashtable<String, String> environment;

    private ConfigurationReader() {
        environment= new Hashtable<>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory");
        environment.put(Context.PROVIDER_URL,"file:/C:/temp");
    }

    public static ConfigurationReader getInstance() {
        if(reader == null) {
            reader = new ConfigurationReader();
        }

        return reader;
    }

    public Integer readIntegerValueForKey(ConfigurationKey key) {
        String valueForKey = readStringValueForKey(key);
        return Integer.parseInt(valueForKey);
    }

    public String readStringValueForKey(ConfigurationKey key) {

        String valueForKey = "";

        try (InitialDirContextCloseable context = new InitialDirContextCloseable(environment)){
            valueForKey = searchForKey(context, key);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return  valueForKey;
    }

    private static String searchForKey(Context context, ConfigurationKey key) {
        String fileName = "config.properties.txt";

        try {
            Object object = context.lookup(fileName);
            Properties props = new Properties();
            props.load(new FileReader(object.toString()));
            String value = props.getProperty(key.getKeyName());
            if (value == null) {
                throw new RuntimeException("The key '"
                        + key.getKeyName() + "' does not exist in configuration file!");
            }
            return props.getProperty(key.getKeyName());
        } catch (NamingException | IOException ex) {
            throw new RuntimeException("Error while reading configuration!", ex);
        }
    }
}
