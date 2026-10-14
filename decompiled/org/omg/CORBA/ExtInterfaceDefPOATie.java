/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AliasDef;
import org.omg.CORBA.Any;
import org.omg.CORBA.AttributeDef;
import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ConstantDef;
import org.omg.CORBA.Contained;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerPackage.Description;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.EnumDef;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtInterfaceDef;
import org.omg.CORBA.ExtInterfaceDefHelper;
import org.omg.CORBA.ExtInterfaceDefOperations;
import org.omg.CORBA.ExtInterfaceDefPOA;
import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescription;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription;
import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.ModuleDef;
import org.omg.CORBA.NativeDef;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.OperationDef;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.Repository;
import org.omg.CORBA.StructDef;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionDef;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.ValueBoxDef;
import org.omg.CORBA.ValueDef;
import org.omg.PortableServer.POA;

public class ExtInterfaceDefPOATie
extends ExtInterfaceDefPOA {
    private ExtInterfaceDefOperations _delegate;
    private POA _poa;

    public ExtInterfaceDefPOATie(ExtInterfaceDefOperations delegate) {
        this._delegate = delegate;
    }

    public ExtInterfaceDefPOATie(ExtInterfaceDefOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public ExtInterfaceDef _this() {
        Object __o = this._this_object();
        ExtInterfaceDef __r = ExtInterfaceDefHelper.narrow(__o);
        return __r;
    }

    @Override
    public ExtInterfaceDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        ExtInterfaceDef __r = ExtInterfaceDefHelper.narrow(__o);
        return __r;
    }

    public ExtInterfaceDefOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ExtInterfaceDefOperations delegate) {
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
    public ValueDef create_value(String id, String name, String version, boolean is_custom, boolean is_abstract, ValueDef base_value, boolean is_truncatable, ValueDef[] abstract_base_values, InterfaceDef[] supported_interfaces, Initializer[] initializers) {
        return this._delegate.create_value(id, name, version, is_custom, is_abstract, base_value, is_truncatable, abstract_base_values, supported_interfaces, initializers);
    }

    @Override
    public String id() {
        return this._delegate.id();
    }

    @Override
    public boolean is_a(String interface_id) {
        return this._delegate.is_a(interface_id);
    }

    @Override
    public String absolute_name() {
        return this._delegate.absolute_name();
    }

    @Override
    public StructDef create_struct(String id, String name, String version, StructMember[] members) {
        return this._delegate.create_struct(id, name, version, members);
    }

    @Override
    public Description[] describe_contents(DefinitionKind limit_type, boolean exclude_inherited, int max_returned_objs) {
        return this._delegate.describe_contents(limit_type, exclude_inherited, max_returned_objs);
    }

    @Override
    public AliasDef create_alias(String id, String name, String version, IDLType original_type) {
        return this._delegate.create_alias(id, name, version, original_type);
    }

    @Override
    public EnumDef create_enum(String id, String name, String version, String[] members) {
        return this._delegate.create_enum(id, name, version, members);
    }

    @Override
    public Contained lookup(String search_name) {
        return this._delegate.lookup(search_name);
    }

    @Override
    public void version(String a) {
        this._delegate.version(a);
    }

    @Override
    public ExtValueDef create_ext_value(String id, String name, String version, boolean is_custom, boolean is_abstract, ValueDef base_value, boolean is_truncatable, ValueDef[] abstract_base_values, InterfaceDef[] supported_interfaces, ExtInitializer[] initializers) {
        return this._delegate.create_ext_value(id, name, version, is_custom, is_abstract, base_value, is_truncatable, abstract_base_values, supported_interfaces, initializers);
    }

    @Override
    public org.omg.CORBA.ContainedPackage.Description describe() {
        return this._delegate.describe();
    }

    @Override
    public void name(String a) {
        this._delegate.name(a);
    }

    @Override
    public ExtFullInterfaceDescription describe_ext_interface() {
        return this._delegate.describe_ext_interface();
    }

    @Override
    public Container defined_in() {
        return this._delegate.defined_in();
    }

    @Override
    public OperationDef create_operation(String id, String name, String version, IDLType result, OperationMode mode, ParameterDescription[] params, ExceptionDef[] exceptions, String[] contexts) {
        return this._delegate.create_operation(id, name, version, result, mode, params, exceptions, contexts);
    }

    @Override
    public Repository containing_repository() {
        return this._delegate.containing_repository();
    }

    @Override
    public ModuleDef create_module(String id, String name, String version) {
        return this._delegate.create_module(id, name, version);
    }

    @Override
    public void move(Container new_container, String new_name, String new_version) {
        this._delegate.move(new_container, new_name, new_version);
    }

    @Override
    public AbstractInterfaceDef create_abstract_interface(String id, String name, String version, AbstractInterfaceDef[] base_interfaces) {
        return this._delegate.create_abstract_interface(id, name, version, base_interfaces);
    }

    @Override
    public String version() {
        return this._delegate.version();
    }

    @Override
    public ConstantDef create_constant(String id, String name, String version, IDLType type, Any value) {
        return this._delegate.create_constant(id, name, version, type, value);
    }

    @Override
    public void destroy() {
        this._delegate.destroy();
    }

    @Override
    public Contained[] lookup_name(String search_name, int levels_to_search, DefinitionKind limit_type, boolean exclude_inherited) {
        return this._delegate.lookup_name(search_name, levels_to_search, limit_type, exclude_inherited);
    }

    @Override
    public TypeCode type() {
        return this._delegate.type();
    }

    @Override
    public void id(String a) {
        this._delegate.id(a);
    }

    @Override
    public Contained[] contents(DefinitionKind limit_type, boolean exclude_inherited) {
        return this._delegate.contents(limit_type, exclude_inherited);
    }

    @Override
    public LocalInterfaceDef create_local_interface(String id, String name, String version, InterfaceDef[] base_interfaces) {
        return this._delegate.create_local_interface(id, name, version, base_interfaces);
    }

    @Override
    public DefinitionKind def_kind() {
        return this._delegate.def_kind();
    }

    @Override
    public InterfaceDef[] base_interfaces() {
        return this._delegate.base_interfaces();
    }

    @Override
    public void base_interfaces(InterfaceDef[] a) {
        this._delegate.base_interfaces(a);
    }

    @Override
    public ValueBoxDef create_value_box(String id, String name, String version, IDLType original_type_def) {
        return this._delegate.create_value_box(id, name, version, original_type_def);
    }

    @Override
    public FullInterfaceDescription describe_interface() {
        return this._delegate.describe_interface();
    }

    @Override
    public AttributeDef create_attribute(String id, String name, String version, IDLType type, AttributeMode mode) {
        return this._delegate.create_attribute(id, name, version, type, mode);
    }

    @Override
    public UnionDef create_union(String id, String name, String version, IDLType discriminator_type, UnionMember[] members) {
        return this._delegate.create_union(id, name, version, discriminator_type, members);
    }

    @Override
    public ExceptionDef create_exception(String id, String name, String version, StructMember[] members) {
        return this._delegate.create_exception(id, name, version, members);
    }

    @Override
    public String name() {
        return this._delegate.name();
    }

    @Override
    public InterfaceDef create_interface(String id, String name, String version, InterfaceDef[] base_interfaces) {
        return this._delegate.create_interface(id, name, version, base_interfaces);
    }

    @Override
    public ExtAttributeDef create_ext_attribute(String id, String name, String version, IDLType type, AttributeMode mode, ExceptionDef[] get_exceptions, ExceptionDef[] set_exceptions) {
        return this._delegate.create_ext_attribute(id, name, version, type, mode, get_exceptions, set_exceptions);
    }

    @Override
    public NativeDef create_native(String id, String name, String version) {
        return this._delegate.create_native(id, name, version);
    }
}

