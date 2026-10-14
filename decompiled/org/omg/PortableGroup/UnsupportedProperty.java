/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableGroup.UnsupportedPropertyHelper;

public final class UnsupportedProperty
extends UserException {
    private static final long serialVersionUID = 1L;
    public NameComponent[] nam;

    public UnsupportedProperty() {
        super(UnsupportedPropertyHelper.id());
    }

    public UnsupportedProperty(String _reason, NameComponent[] nam) {
        super(_reason);
        this.nam = nam;
    }

    public UnsupportedProperty(NameComponent[] nam) {
        super(UnsupportedPropertyHelper.id());
        this.nam = nam;
    }
}

