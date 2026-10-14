/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;
import java.util.List;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CachePeer;

public final class RMICachePeer_Stub
extends RemoteStub
implements CachePeer,
Remote {
    private static final long serialVersionUID = 2L;
    private static Method $method_getElements_0;
    private static Method $method_getGuid_1;
    private static Method $method_getKeys_2;
    private static Method $method_getName_3;
    private static Method $method_getQuiet_4;
    private static Method $method_getUrl_5;
    private static Method $method_getUrlBase_6;
    private static Method $method_put_7;
    private static Method $method_remove_8;
    private static Method $method_removeAll_9;
    private static Method $method_send_10;
    static /* synthetic */ Class class$net$sf$ehcache$distribution$CachePeer;
    static /* synthetic */ Class class$java$util$List;
    static /* synthetic */ Class class$java$io$Serializable;
    static /* synthetic */ Class class$net$sf$ehcache$Element;

    static {
        try {
            $method_getElements_0 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("getElements", class$java$util$List != null ? class$java$util$List : (class$java$util$List = RMICachePeer_Stub.class$("java.util.List")));
            $method_getGuid_1 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("getGuid", new Class[0]);
            $method_getKeys_2 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("getKeys", new Class[0]);
            $method_getName_3 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("getName", new Class[0]);
            $method_getQuiet_4 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("getQuiet", class$java$io$Serializable != null ? class$java$io$Serializable : (class$java$io$Serializable = RMICachePeer_Stub.class$("java.io.Serializable")));
            $method_getUrl_5 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("getUrl", new Class[0]);
            $method_getUrlBase_6 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("getUrlBase", new Class[0]);
            $method_put_7 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("put", class$net$sf$ehcache$Element != null ? class$net$sf$ehcache$Element : (class$net$sf$ehcache$Element = RMICachePeer_Stub.class$("net.sf.ehcache.Element")));
            $method_remove_8 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("remove", class$java$io$Serializable != null ? class$java$io$Serializable : (class$java$io$Serializable = RMICachePeer_Stub.class$("java.io.Serializable")));
            $method_removeAll_9 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("removeAll", new Class[0]);
            $method_send_10 = (class$net$sf$ehcache$distribution$CachePeer != null ? class$net$sf$ehcache$distribution$CachePeer : (class$net$sf$ehcache$distribution$CachePeer = RMICachePeer_Stub.class$("net.sf.ehcache.distribution.CachePeer"))).getMethod("send", class$java$util$List != null ? class$java$util$List : (class$java$util$List = RMICachePeer_Stub.class$("java.util.List")));
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new NoSuchMethodError("stub class initialization failed");
        }
    }

    public RMICachePeer_Stub(RemoteRef remoteRef) {
        super(remoteRef);
    }

    static /* synthetic */ Class class$(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    public List getElements(List list) throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getElements_0, new Object[]{list}, -4376068039852039191L);
            return (List)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getGuid() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getGuid_1, null, -5018535278819932267L);
            return (String)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public List getKeys() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getKeys_2, null, 3157475003063493488L);
            return (List)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getName() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getName_3, null, 6317137956467216454L);
            return (String)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Element getQuiet(Serializable serializable) throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getQuiet_4, new Object[]{serializable}, -2489236551407760788L);
            return (Element)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getUrl() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getUrl_5, null, -3916922505016776705L);
            return (String)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getUrlBase() throws RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_getUrlBase_6, null, -2605516070532365148L);
            return (String)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void put(Element element) throws IllegalArgumentException, IllegalStateException, RemoteException {
        try {
            this.ref.invoke(this, $method_put_7, new Object[]{element}, -9129621168805254110L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean remove(Serializable serializable) throws IllegalStateException, RemoteException {
        try {
            Object object = this.ref.invoke(this, $method_remove_8, new Object[]{serializable}, 878263641853290417L);
            return (Boolean)object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void removeAll() throws IllegalStateException, RemoteException {
        try {
            this.ref.invoke(this, $method_removeAll_9, null, -8769766718210093917L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void send(List list) throws RemoteException {
        try {
            this.ref.invoke(this, $method_send_10, new Object[]{list}, 5956270039998675550L);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }
}

