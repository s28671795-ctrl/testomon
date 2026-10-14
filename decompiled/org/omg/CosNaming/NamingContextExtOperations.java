/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextOperations;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public interface NamingContextExtOperations
extends NamingContextOperations {
    public String to_string(NameComponent[] var1) throws InvalidName;

    public NameComponent[] to_name(String var1) throws InvalidName;

    public String to_url(String var1, String var2) throws InvalidName, InvalidAddress;

    public Object resolve_str(String var1) throws NotFound, CannotProceed, InvalidName;
}

