/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeDef;
import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ContainedOperations;
import org.omg.CORBA.ContainerOperations;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeOperations;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription;
import org.omg.CORBA.OperationDef;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.ParameterDescription;

public interface InterfaceDefOperations
extends ContainerOperations,
ContainedOperations,
IDLTypeOperations {
    public InterfaceDef[] base_interfaces();

    public void base_interfaces(InterfaceDef[] var1);

    public boolean is_a(String var1);

    public FullInterfaceDescription describe_interface();

    public AttributeDef create_attribute(String var1, String var2, String var3, IDLType var4, AttributeMode var5);

    public OperationDef create_operation(String var1, String var2, String var3, IDLType var4, OperationMode var5, ParameterDescription[] var6, ExceptionDef[] var7, String[] var8);
}

