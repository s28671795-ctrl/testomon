/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;

public final class CosNamingLocator
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public NamingContext name_service;
    public NameComponent[] the_name;

    public CosNamingLocator() {
    }

    public CosNamingLocator(NamingContext name_service, NameComponent[] the_name) {
        this.name_service = name_service;
        this.the_name = the_name;
    }
}

