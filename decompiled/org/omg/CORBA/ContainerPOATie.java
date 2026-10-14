/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AliasDef;
import org.omg.CORBA.Any;
import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.Contained;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.ContainerOperations;
import org.omg.CORBA.ContainerPOA;
import org.omg.CORBA.ContainerPackage.Description;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.EnumDef;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.ModuleDef;
import org.omg.CORBA.NativeDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StructDef;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.UnionDef;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.ValueBoxDef;
import org.omg.CORBA.ValueDef;
import org.omg.PortableServer.POA;

public class ContainerPOATie
extends ContainerPOA {
    private ContainerOperations _delegate;
    private POA _poa;

    public ContainerPOATie(ContainerOperations delegate) {
        this._delegate = delegate;
    }

    public ContainerPOATie(ContainerOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public Container _this() {
        Object __o = this._this_object();
        Container __r = ContainerHelper.narrow(__o);
        return __r;
    }

    @Override
    public Container _this(ORB orb) {
        Object __o = this._this_object(orb);
        Container __r = ContainerHelper.narrow(__o);
        return __r;
    }

    public ContainerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ContainerOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public POA _default_POA() {
        if (this._poa != null) {
            return this._poa;
        }
        return super._default_POA();
    }

    @Override
    public StructDef create_struct(String id, String name, String version, StructMember[] members) {
        return this._delegate.create_struct(id, name, version, members);
    }

    @Override
    public ConstantDef create_constant(String id, String name, String version, IDLType type, Any value) {
        return this._delegate.create_constant(id, name, version, type, value);
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }

    @Override
    public AliasDef create_alias(String id, String name, String version, IDLType original_type) {
        return this._delegate.create_alias(id, name, version, original_type);
    }

    @Override
    public UnionDef create_union(String id, String name, String version, IDLType discriminator_type, UnionMember[] members) {
        return this._delegate.create_union(id, name, version, discriminator_type, members);
    }

    @Override
    public Contained lookup(String search_name) {
        return this._delegate.lookup(search_name);
    }

    @Override
    public NativeDef create_native(String id, String name, String version) {
        return this._delegate.create_native(id, name, version);
    }

    @Override
    public ExtValueDef create_ext_value(String id, String name, String version, boolean is_custom, boolean is_abstract, ValueDef base_value, boolean is_truncatable, ValueDef[] abstract_base_values, InterfaceDef[] supported_interfaces, ExtInitializer[] initializers) {
        return this._delegate.create_ext_value(id, name, version, is_custom, is_abstract, base_value, is_truncatable, abstract_base_values, supported_interfaces, initializers);
    }

    @Override
    public ValueBoxDef create_value_box(String id, String name, String version, IDLType original_type_def) {
        return this._delegate.create_value_box(id, name, version, original_type_def);
    }

    @Override
    public Contained[] lookup_name(String search_name, int levels_to_search, DefinitionKind limit_type, boolean exclude_inherited) {
        return this._delegate.lookup_name(search_name, levels_to_search, limit_type, exclude_inherited);
    }

    @Override
    public AbstractInterfaceDef create_abstract_interface(String id, String name, String version, AbstractInterfaceDef[] base_interfaces) {
        return this._delegate.create_abstract_interface(id, name, version, base_interfaces);
    }

    @Override
    public Description[] describe_contents(DefinitionKind limit_type, boolean exclude_inherited, int max_returned_objs) {
        return this._delegate.describe_contents(limit_type, exclude_inherited, max_returned_objs);
    }

    @Override
    public ModuleDef create_module(String id, String name, String version) {
        return this._delegate.create_module(id, name, version);
    }

    @Override
    public ValueDef create_value(String id, String name, String version, boolean is_custom, boolean is_abstract, ValueDef base_value, boolean is_truncatable, ValueDef[] abstract_base_values, InterfaceDef[] supported_interfaces, Initializer[] initializers) {
        return this._delegate.create_value(id, name, version, is_custom, is_abstract, base_value, is_truncatable, abstract_base_values, supported_interfaces, initializers);
    }

    @Override
    public Contained[] contents(DefinitionKind limit_type, boolean exclude_inherited) {
        return this._delegate.contents(limit_type, exclude_inherited);
    }

    @Override
    public EnumDef create_enum(String id, String name, String version, String[] members) {
        return this._delegate.create_enum(id, name, version, members);
    }

    @Override
    public InterfaceDef create_interface(String id, String name, String version, InterfaceDef[] base_interfaces) {
        return this._delegate.create_interface(id, name, version, base_interfaces);
    }

    @Override
    public LocalInterfaceDef create_local_interface(String id, String name, String version, InterfaceDef[] base_interfaces) {
        return this._delegate.create_local_interface(id, name, version, base_interfaces);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public ExceptionDef create_exception(String id, String name, String version, StructMember[] members) {
        return this._delegate.create_exception(id, name, version, members);
    }
}

