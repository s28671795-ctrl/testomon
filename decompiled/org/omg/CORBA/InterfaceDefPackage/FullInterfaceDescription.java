/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.InterfaceDefPackage;

import org.omg.CORBA.AttributeDescription;
import org.omg.CORBA.OperationDescription;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.IDLEntity;

public final class FullInterfaceDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public OperationDescription[] operations;
    public AttributeDescription[] attributes;
    public String[] base_interfaces;
    public TypeCode type;

    public FullInterfaceDescription() {
    }

    public FullInterfaceDescription(String name, String id, String defined_in, String version, OperationDescription[] operations, AttributeDescription[] attributes, String[] base_interfaces, TypeCode type) {
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

