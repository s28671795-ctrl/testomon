/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableGroup.GenericFactory;
import org.omg.PortableGroup.Property;

public final class FactoryInfo
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public GenericFactory the_factory;
    public NameComponent[] the_location;
    public Property[] the_criteria;

    public FactoryInfo() {
    }

    public FactoryInfo(GenericFactory the_factory, NameComponent[] the_location, Property[] the_criteria) {
        this.the_factory = the_factory;
        this.the_location = the_location;
        this.the_criteria = the_criteria;
    }
}

