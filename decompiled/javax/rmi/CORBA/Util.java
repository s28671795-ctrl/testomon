/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.net.MalformedURLException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClassLoader;
import java.security.AccessController;
import java.util.Properties;
import javax.rmi.CORBA.GetORBPropertiesFileAction;
import javax.rmi.CORBA.GetPropertyAction;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.UtilDelegate;
import javax.rmi.CORBA.ValueHandler;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class Util {
    private static UtilDelegate utilDelegate = null;
    private static final String UtilClassKey = "javax.rmi.CORBA.UtilClass";

    private Util() {
    }

    public static RemoteException mapSystemException(SystemException ex) {
        if (utilDelegate != null) {
            return utilDelegate.mapSystemException(ex);
        }
        return null;
    }

    public static void writeAny(OutputStream out, Object obj) {
        if (utilDelegate != null) {
            utilDelegate.writeAny(out, obj);
        }
    }

    public static Object readAny(InputStream in) {
        if (utilDelegate != null) {
            return utilDelegate.readAny(in);
        }
        return null;
    }

    public static void writeRemoteObject(OutputStream out, Object obj) {
        if (utilDelegate != null) {
            utilDelegate.writeRemoteObject(out, obj);
        }
    }

    public static void writeAbstractObject(OutputStream out, Object obj) {
        if (utilDelegate != null) {
            utilDelegate.writeAbstractObject(out, obj);
        }
    }

    public static void registerTarget(Tie tie, Remote target) {
        if (utilDelegate != null) {
            utilDelegate.registerTarget(tie, target);
        }
    }

    public static void unexportObject(Remote target) throws NoSuchObjectException {
        if (utilDelegate != null) {
            utilDelegate.unexportObject(target);
        }
    }

    public static Tie getTie(Remote target) {
        if (utilDelegate != null) {
            return utilDelegate.getTie(target);
        }
        return null;
    }

    public static ValueHandler createValueHandler() {
        if (utilDelegate != null) {
            return utilDelegate.createValueHandler();
        }
        return null;
    }

    public static String getCodebase(Class<?> clz) {
        if (utilDelegate != null) {
            return utilDelegate.getCodebase(clz);
        }
        return null;
    }

    public static Class<?> loadClass(String className, String remoteCodebase, ClassLoader loader) throws ClassNotFoundException {
        if (utilDelegate != null) {
            return utilDelegate.loadClass(className, remoteCodebase, loader);
        }
        return null;
    }

    public static boolean isLocal(Stub stub) throws RemoteException {
        if (utilDelegate != null) {
            return utilDelegate.isLocal(stub);
        }
        return false;
    }

    public static RemoteException wrapException(Throwable orig) {
        if (utilDelegate != null) {
            return utilDelegate.wrapException(orig);
        }
        return null;
    }

    public static Object[] copyObjects(Object[] obj, ORB orb) throws RemoteException {
        if (utilDelegate != null) {
            return utilDelegate.copyObjects(obj, orb);
        }
        return null;
    }

    public static Object copyObject(Object obj, ORB orb) throws RemoteException {
        if (utilDelegate != null) {
            return utilDelegate.copyObject(obj, orb);
        }
        return null;
    }

    private static Object createDelegateIfSpecified(String classKey) {
        Properties props;
        String className = (String)AccessController.doPrivileged(new GetPropertyAction(classKey));
        if (className == null && (props = Util.getORBPropertiesFile()) != null) {
            className = props.getProperty(classKey);
        }
        if (className == null) {
            return new org.jboss.com.sun.corba.se.impl.javax.rmi.CORBA.Util();
        }
        try {
            return Util.loadDelegateClass(className).newInstance();
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
            return Class.forName(className, false, Util.class.getClassLoader());
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
        return (Properties)AccessController.doPrivileged(new GetORBPropertiesFileAction());
    }

    static {
        utilDelegate = (UtilDelegate)Util.createDelegateIfSpecified(UtilClassKey);
    }
}

