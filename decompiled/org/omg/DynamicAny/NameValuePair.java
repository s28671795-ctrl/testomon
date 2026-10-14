/*
 * Decompiled with CFR 0.152.
 */
package org.omg.DynamicAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.portable.IDLEntity;

public final class NameValuePair
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String id = "";
    public Any value;

    public NameValuePair() {
    }

    public NameValuePair(String id, Any value) {
        this.id = id;
        this.value = value;
    }
}

