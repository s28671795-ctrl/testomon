/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableGroup.NoFactoryHelper;

public final class NoFactory
extends UserException {
    private static final long serialVersionUID = 1L;
    public NameComponent[] the_location;
    public String type_id = "";

    public NoFactory() {
        super(NoFactoryHelper.id());
    }

    public NoFactory(String _reason, NameComponent[] the_location, String type_id) {
        super(_reason);
        this.the_location = the_location;
        this.type_id = type_id;
    }

    public NoFactory(NameComponent[] the_location, String type_id) {
        super(NoFactoryHelper.id());
        this.the_location = the_location;
        this.type_id = type_id;
    }
}

