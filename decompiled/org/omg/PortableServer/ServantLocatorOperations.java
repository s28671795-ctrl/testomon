/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
import org.omg.PortableServer.ServantManagerOperations;

public interface ServantLocatorOperations
extends ServantManagerOperations {
    public Servant preinvoke(byte[] var1, POA var2, String var3, CookieHolder var4) throws ForwardRequest;

    public void postinvoke(byte[] var1, POA var2, String var3, Object var4, Servant var5) throws ForwardRequest;
}

