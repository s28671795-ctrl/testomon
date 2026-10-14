/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerFactoryPackage.ManagerAlreadyExists;

public interface POAManagerFactoryOperations {
    public POAManager create_POAManager(String var1, Policy[] var2) throws ManagerAlreadyExists, PolicyError;

    public POAManager[] list();

    public POAManager find(String var1);
}

