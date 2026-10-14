/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.portable.IDLEntity;

public final class InterfaceDescription
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public String id = "";
    public String defined_in = "";
    public String version = "";
    public String[] base_interfaces;

    public InterfaceDescription() {
    }

    public InterfaceDescription(String name, String id, String defined_in, String version, String[] base_interfaces) {
        this.name = name;
        this.id = id;
        this.defined_in = defined_in;
        this.version = version;
        this.base_interfaces = base_interfaces;
    }
}

