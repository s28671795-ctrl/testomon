/*
 * Decompiled with CFR 0.152.
 */
package javax.management.j2ee;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;

public interface ListenerRegistration
extends Serializable {
    public void addNotificationListener(ObjectName var1, NotificationListener var2, NotificationFilter var3, Object var4) throws InstanceNotFoundException, RemoteException;

    public void removeNotificationListener(ObjectName var1, NotificationListener var2) throws InstanceNotFoundException, ListenerNotFoundException, RemoteException;
}

