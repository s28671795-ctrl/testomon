/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.portable.IDLEntity;

public final class AuthorizationElement
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int the_type;
    public byte[] the_element;

    public AuthorizationElement() {
    }

    public AuthorizationElement(int the_type, byte[] the_element) {
        this.the_type = the_type;
        this.the_element = the_element;
    }
}

