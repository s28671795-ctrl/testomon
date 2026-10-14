/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ExtValueDefPackage;

import org.omg.CORBA.ExtAttributeDescription;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.OperationDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.portable.IDLEntity;

public final class ExtFullValueDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public boolean is_abstract;
    public boolean is_custom;
    public String defined_in = "";
    public String version = "";
    public OperationDescription[] operations;
    public ExtAttributeDescription[] attributes;
    public ValueMember[] members;
    public ExtInitializer[] initializers;
    public String[] supported_interfaces;
    public String[] abstract_base_values;
    public boolean is_truncatable;
    public String base_value = "";
    public TypeCode type;

    public ExtFullValueDescription() {
    }

    public ExtFullValueDescription(String name, String id, boolean is_abstract, boolean is_custom, String defined_in, String version, OperationDescription[] operations, ExtAttributeDescription[] attributes, ValueMember[] members, ExtInitializer[] initializers, String[] supported_interfaces, String[] abstract_base_values, boolean is_truncatable, String base_value, TypeCode type) {
        this.name = name;
        this.id = id;
        this.is_abstract = is_abstract;
        this.is_custom = is_custom;
        this.defined_in = defined_in;
        this.version = version;
        this.operations = operations;
        this.attributes = attributes;
        this.members = members;
        this.initializers = initializers;
        this.supported_interfaces = supported_interfaces;
        this.abstract_base_values = abstract_base_values;
        this.is_truncatable = is_truncatable;
        this.base_value = base_value;
        this.type = type;
    }
}

