/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.InterfaceAttrExtensionPackage;

import org.omg.CORBA.ExtAttributeDescription;
import org.omg.CORBA.OperationDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class ExtFullInterfaceDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public OperationDescription[] operations;
    public ExtAttributeDescription[] attributes;
    public String[] base_interfaces;
    public TypeCode type;

    public ExtFullInterfaceDescription() {
    }

    public ExtFullInterfaceDescription(String name, String id, String defined_in, String version, OperationDescription[] operations, ExtAttributeDescription[] attributes, String[] base_interfaces, TypeCode type) {
        this.name = name;
        this.id = id;
        this.defined_in = defined_in;
        this.version = version;
        this.operations = operations;
        this.attributes = attributes;
        this.base_interfaces = base_interfaces;
        this.type = type;
    }
}

