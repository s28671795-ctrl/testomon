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
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.OperationDef;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.ValueDef;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.ValueMemberDef;

public interface ValueDefOperations
extends ContainerOperations,
ContainedOperations,
IDLTypeOperations {
    public InterfaceDef[] supported_interfaces();

    public void supported_interfaces(InterfaceDef[] var1);

    public Initializer[] initializers();

    public void initializers(Initializer[] var1);

    public ValueDef base_value();

    public void base_value(ValueDef var1);

    public ValueDef[] abstract_base_values();

    public void abstract_base_values(ValueDef[] var1);

    public boolean is_abstract();

    public void is_abstract(boolean var1);

    public boolean is_custom();

    public void is_custom(boolean var1);

    public boolean is_truncatable();

    public void is_truncatable(boolean var1);

    public boolean is_a(String var1);

    public FullValueDescription describe_value();

    public ValueMemberDef create_value_member(String var1, String var2, String var3, IDLType var4, short var5);

    public AttributeDef create_attribute(String var1, String var2, String var3, IDLType var4, AttributeMode var5);

    public OperationDef create_operation(String var1, String var2, String var3, IDLType var4, OperationMode var5, ParameterDescription[] var6, ExceptionDef[] var7, String[] var8);
}

