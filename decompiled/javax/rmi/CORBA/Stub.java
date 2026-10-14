/*
 * Decompiled with CFR 0.152.
 */
package javax.rmi.CORBA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClassLoader;
import java.security.AccessController;
import java.util.Properties;
import javax.rmi.CORBA.GetORBPropertiesFileAction;
import javax.rmi.CORBA.GetPropertyAction;
import javax.rmi.CORBA.StubDelegate;
import org.jboss.com.sun.corba.se.impl.javax.rmi.CORBA.StubDelegateImpl;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.ORB;
import org.omg.CORBA_2_3.portable.ObjectImpl;

public abstract class Stub
extends ObjectImpl
implements Serializable {
    private static final long serialVersionUID = 1087775603798577179L;
    private transient StubDelegate stubDelegate = null;
    private static Class<?> stubDelegateClass = null;
    private static final String StubClassKey = "javax.rmi.CORBA.StubClass";

    @Override
    public int hashCode() {
        if (this.stubDelegate == null) {
            this.setDefaultDelegate();
        }
        if (this.stubDelegate != null) {
            return this.stubDelegate.hashCode(this);
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.stubDelegate == null) {
            this.setDefaultDelegate();
        }
        if (this.stubDelegate != null) {
            return this.stubDelegate.equals(this, obj);
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.stubDelegate == null) {
            this.setDefaultDelegate();
        }
        if (this.stubDelegate != null) {
            String ior = this.stubDelegate.toString(this);
            if (ior == null) {
                return super.toString();
            }
            return ior;
        }
        return super.toString();
    }

    public void connect(ORB orb) throws RemoteException {
        if (this.stubDelegate == null) {
            this.setDefaultDelegate();
        }
        if (this.stubDelegate != null) {
            this.stubDelegate.connect(this, orb);
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        if (this.stubDelegate == null) {
            this.setDefaultDelegate();
        }
        if (this.stubDelegate != null) {
            this.stubDelegate.readObject(this, stream);
        }
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        if (this.stubDelegate == null) {
            this.setDefaultDelegate();
        }
        if (this.stubDelegate != null) {
            this.stubDelegate.writeObject(this, stream);
        }
    }

    private void setDefaultDelegate() {
        if (stubDelegateClass != null) {
            try {
                this.stubDelegate = (StubDelegate)stubDelegateClass.newInstance();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private static Object createDelegateIfSpecified(String classKey) {
        Properties props;
        String className = (String)AccessController.doPrivileged(new GetPropertyAction(classKey));
        if (className == null && (props = Stub.getORBPropertiesFile()) != null) {
            className = props.getProperty(classKey);
        }
        if (className == null) {
            return new StubDelegateImpl();
        }
        try {
            return Stub.loadDelegateClass(className).newInstance();
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
            return Class.forName(className, false, Stub.class.getClassLoader());
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
        Object stubDelegateInstance = Stub.createDelegateIfSpecified(StubClassKey);
        if (stubDelegateInstance != null) {
            stubDelegateClass = stubDelegateInstance.getClass();
        }
    }
}

