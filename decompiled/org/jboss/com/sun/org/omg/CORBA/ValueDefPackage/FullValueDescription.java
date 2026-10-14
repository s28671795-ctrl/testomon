/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA.ValueDefPackage;

import org.jboss.com.sun.org.omg.CORBA.AttributeDescription;
import org.jboss.com.sun.org.omg.CORBA.Initializer;
import org.jboss.com.sun.org.omg.CORBA.OperationDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.portable.IDLEntity;

public final class FullValueDescription
implements IDLEntity {
    public String name = null;
    public String id = null;
    public boolean is_abstract = false;
    public boolean is_custom = false;
    public String defined_in = null;
    public String version = null;
    public OperationDescription[] operations = null;
    public AttributeDescription[] attributes = null;
    public ValueMember[] members = null;
    public Initializer[] initializers = null;
    public String[] supported_interfaces = null;
    public String[] abstract_base_values = null;
    public boolean is_truncatable = false;
    public String base_value = null;
    public TypeCode type = null;

    public FullValueDescription() {
    }

    public FullValueDescription(String _name, String _id, boolean _is_abstract, boolean _is_custom, String _defined_in, String _version, OperationDescription[] _operations, AttributeDescription[] _attributes, ValueMember[] _members, Initializer[] _initializers, String[] _supported_interfaces, String[] _abstract_base_values, boolean _is_truncatable, String _base_value, TypeCode _type) {
        this.name = _name;
        this.id = _id;
        this.is_abstract = _is_abstract;
        this.is_custom = _is_custom;
        this.defined_in = _defined_in;
        this.version = _version;
        this.operations = _operations;
        this.attributes = _attributes;
        this.members = _members;
        this.initializers = _initializers;
        this.supported_interfaces = _supported_interfaces;
        this.abstract_base_values = _abstract_base_values;
        this.is_truncatable = _is_truncatable;
        this.base_value = _base_value;
        this.type = _type;
    }
}

