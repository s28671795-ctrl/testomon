/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AliasDef;
import org.omg.CORBA.Any;
import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.Contained;
import org.omg.CORBA.ContainerPackage.Description;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.EnumDef;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IRObjectOperations;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.ModuleDef;
import org.omg.CORBA.NativeDef;
import org.omg.CORBA.StructDef;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.UnionDef;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.ValueBoxDef;
import org.omg.CORBA.ValueDef;

public interface ContainerOperations
extends IRObjectOperations {
    public Contained lookup(String var1);

    public Contained[] contents(DefinitionKind var1, boolean var2);

    public Contained[] lookup_name(String var1, int var2, DefinitionKind var3, boolean var4);

    public Description[] describe_contents(DefinitionKind var1, boolean var2, int var3);

    public ModuleDef create_module(String var1, String var2, String var3);

    public ConstantDef create_constant(String var1, String var2, String var3, IDLType var4, Any var5);

    public StructDef create_struct(String var1, String var2, String var3, StructMember[] var4);

    public UnionDef create_union(String var1, String var2, String var3, IDLType var4, UnionMember[] var5);

    public EnumDef create_enum(String var1, String var2, String var3, String[] var4);

    public AliasDef create_alias(String var1, String var2, String var3, IDLType var4);

    public InterfaceDef create_interface(String var1, String var2, String var3, InterfaceDef[] var4);

    public ValueDef create_value(String var1, String var2, String var3, boolean var4, boolean var5, ValueDef var6, boolean var7, ValueDef[] var8, InterfaceDef[] var9, Initializer[] var10);

    public ValueBoxDef create_value_box(String var1, String var2, String var3, IDLType var4);

    public ExceptionDef create_exception(String var1, String var2, String var3, StructMember[] var4);

    public NativeDef create_native(String var1, String var2, String var3);

    public AbstractInterfaceDef create_abstract_interface(String var1, String var2, String var3, AbstractInterfaceDef[] var4);

    public LocalInterfaceDef create_local_interface(String var1, String var2, String var3, InterfaceDef[] var4);

    public ExtValueDef create_ext_value(String var1, String var2, String var3, boolean var4, boolean var5, ValueDef var6, boolean var7, ValueDef[] var8, InterfaceDef[] var9, ExtInitializer[] var10);
}

