/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.portable;

import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public interface Delegate {
    public ORB orb(Servant var1);

    public Object this_object(Servant var1);

    public POA poa(Servant var1);

    public byte[] object_id(Servant var1);

    public POA default_POA(Servant var1);

    public boolean is_a(Servant var1, String var2);

    public boolean non_existent(Servant var1);

    public Object get_component(Servant var1);

    public InterfaceDef get_interface(Servant var1);

    public Object get_interface_def(Servant var1);

    public String repository_id(Servant var1);
}

