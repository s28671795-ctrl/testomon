/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.jaas;

import java.io.Serializable;
import org.ldaptive.LdapUtils;

public class LdapCredential
implements Serializable {
    private static final int HASH_CODE_SEED = 401;
    private static final long serialVersionUID = 1965949592374728021L;
    private final Object credential;

    public LdapCredential(Object o) {
        this.credential = o;
    }

    public Object getCredential() {
        return this.credential;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapCredential) {
            LdapCredential v = (LdapCredential)o;
            return LdapUtils.areEqual(this.credential, v.credential);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(401, this.credential);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "credential=" + this.credential + "]";
    }
}

