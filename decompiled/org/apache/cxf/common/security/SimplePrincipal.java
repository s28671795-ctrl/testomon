/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.security;

import java.io.Serializable;
import java.security.Principal;

public class SimplePrincipal
implements Principal,
Serializable {
    private static final long serialVersionUID = -5171549568204891853L;
    private String name;

    public SimplePrincipal(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Principal name can not be null");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SimplePrincipal)) {
            return false;
        }
        return this.name.equals(((SimplePrincipal)obj).name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}

