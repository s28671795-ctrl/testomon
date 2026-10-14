/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescription;

public interface InterfaceAttrExtensionOperations {
    public ExtFullInterfaceDescription describe_ext_interface();

    public ExtAttributeDef create_ext_attribute(String var1, String var2, String var3, IDLType var4, AttributeMode var5, ExceptionDef[] var6, ExceptionDef[] var7);
}

