/*
 * Decompiled with CFR 0.152.
 */
package org.omg.MIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.Version;
import org.omg.IOP.TaggedComponent;

public final class UIPMC_ProfileBody
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public Version miop_version;
    public String the_address = "";
    public short the_port;
    public TaggedComponent[] components;

    public UIPMC_ProfileBody() {
    }

    public UIPMC_ProfileBody(Version miop_version, String the_address, short the_port, TaggedComponent[] components) {
        this.miop_version = miop_version;
        this.the_address = the_address;
        this.the_port = the_port;
        this.components = components;
    }
}

