/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.Security.AttributeType;

public final class SecAttribute
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public AttributeType attribute_type;
    public byte[] defining_authority;
    public byte[] value;

    public SecAttribute() {
    }

    public SecAttribute(AttributeType attribute_type, byte[] defining_authority, byte[] value) {
        this.attribute_type = attribute_type;
        this.defining_authority = defining_authority;
        this.value = value;
    }
}

