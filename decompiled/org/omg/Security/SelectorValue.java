/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.Any;
import org.omg.CORBA.portable.IDLEntity;

public final class SelectorValue
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int selector;
    public Any value;

    public SelectorValue() {
    }

    public SelectorValue(int selector, Any value) {
        this.selector = selector;
        this.value = value;
    }
}

