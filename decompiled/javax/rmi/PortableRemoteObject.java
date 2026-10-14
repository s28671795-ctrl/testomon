/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi;

import java.net.MalformedURLException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClassLoader;
import java.security.AccessController;
import java.util.Properties;
import javax.rmi.CORBA.GetPropertyAction;
import javax.rmi.CORBA.PortableRemoteObjectDelegate;
import javax.rmi.GetORBPropertiesFileAction;
import org.omg.CORBA.INITIALIZE;

public class PortableRemoteObject {
    private static PortableRemoteObjectDelegate proDelegate = null;
    private static final String PortableRemoteObjectClassKey = "javax.rmi.CORBA.PortableRemoteObjectClass";

    protected PortableRemoteObject() throws RemoteException {
        if (proDelegate != null) {
            PortableRemoteObject.exportObject((Remote)((Object)this));
        }
    }

    public static void exportObject(Remote obj) throws RemoteException {
        if (proDelegate != null) {
            proDelegate.exportObject(obj);
        }
    }

    public static Remote toStub(Remote obj) throws NoSuchObjectException {
        if (proDelegate != null) {
            return proDelegate.toStub(obj);
        }
        return null;
    }

    public static void unexportObject(Remote obj) throws NoSuchObjectException {
        if (proDelegate != null) {
            proDelegate.unexportObject(obj);
        }
    }

    public static Object narrow(Object narrowFrom, Class<?> narrowTo) throws ClassCastException {
        if (proDelegate != null) {
            return proDelegate.narrow(narrowFrom, narrowTo);
        }
        return null;
    }

    public static void connect(Remote target, Remote source2) throws RemoteException {
        if (proDelegate != null) {
            proDelegate.connect(target, source2);
        }
    }

    private static Object createDelegateIfSpecified(String classKey) {
        Properties props;
        String className = (String)AccessController.doPrivileged(new GetPropertyAction(classKey));
        if (className == null && (props = PortableRemoteObject.getORBPropertiesFile()) != null) {
            className = props.getProperty(classKey);
        }
        if (className == null) {
            return new org.jboss.com.sun.corba.se.impl.javax.rmi.PortableRemoteObject();
        }
        try {
            return PortableRemoteObject.loadDelegateClass(className).newInstance();
        }
        catch (ClassNotFoundException ex) {
            INITIALIZE exc = new INITIALIZE("Cannot instantiate " + className);
            exc.initCause(ex);
            throw exc;
        }
        catch (Exception ex) {
            INITIALIZE exc = new INITIALIZE("Error while instantiating" + className);
            exc.initCause(ex);
            throw exc;
        }
    }

    private static Class<?> loadDelegateClass(String className) throws ClassNotFoundException {
        try {
            return Class.forName(className, false, PortableRemoteObject.class.getClassLoader());
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                return RMIClassLoader.loadClass((String)null, className);
            }
            catch (MalformedURLException e) {
                String msg = "Could not load " + className + ": " + e.toString();
                ClassNotFoundException exc = new ClassNotFoundException(msg);
                throw exc;
            }
        }
    }

    private static Properties getORBPropertiesFile() {
        return AccessController.doPrivileged(new GetORBPropertiesFileAction());
    }

    static {
        proDelegate = (PortableRemoteObjectDelegate)PortableRemoteObject.createDelegateIfSpecified(PortableRemoteObjectClassKey);
    }
}

