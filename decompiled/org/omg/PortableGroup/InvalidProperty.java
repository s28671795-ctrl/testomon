/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.UserException;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableGroup.InvalidPropertyHelper;

public final class InvalidProperty
extends UserException {
    private static final long serialVersionUID = 1L;
    public NameComponent[] nam;
    public Any val;

    public InvalidProperty() {
        super(InvalidPropertyHelper.id());
    }

    public InvalidProperty(String _reason, NameComponent[] nam, Any val) {
        super(_reason);
        this.nam = nam;
        this.val = val;
    }

    public InvalidProperty(NameComponent[] nam, Any val) {
        super(InvalidPropertyHelper.id());
        this.nam = nam;
        this.val = val;
    }
}

