/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ejb.CreateException
 *  javax.ejb.EJBHome
 */
package javax.management.j2ee;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.management.j2ee.Management;

public interface ManagementHome
extends EJBHome {
    public Management create() throws CreateException, RemoteException;
}

