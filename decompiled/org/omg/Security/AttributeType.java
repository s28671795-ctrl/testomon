/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.Security.ExtensibleFamily;

public final class AttributeType
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public ExtensibleFamily attribute_family;
    public int attribute_type;

    public AttributeType() {
    }

    public AttributeType(ExtensibleFamily attribute_family, int attribute_type) {
        this.attribute_family = attribute_family;
        this.attribute_type = attribute_type;
    }
}

