/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;

public interface POAPolicyMediator {
    public Policies getPolicies();

    public int getScid();

    public int getServerId();

    public Object getInvocationServant(byte[] var1, String var2) throws ForwardRequest;

    public void returnServant();

    public void etherealizeAll();

    public void clearAOM();

    public ServantManager getServantManager() throws WrongPolicy;

    public void setServantManager(ServantManager var1) throws WrongPolicy;

    public Servant getDefaultServant() throws NoServant, WrongPolicy;

    public void setDefaultServant(Servant var1) throws WrongPolicy;

    public void activateObject(byte[] var1, Servant var2) throws ObjectAlreadyActive, ServantAlreadyActive, WrongPolicy;

    public Servant deactivateObject(byte[] var1) throws ObjectNotActive, WrongPolicy;

    public byte[] newSystemId() throws WrongPolicy;

    public byte[] servantToId(Servant var1) throws ServantNotActive, WrongPolicy;

    public Servant idToServant(byte[] var1) throws ObjectNotActive, WrongPolicy;
}

