/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.LdapAttribute;

public class AttributeModification {
    private final Type operation;
    private final LdapAttribute attribute;

    public AttributeModification(Type type, LdapAttribute attr) {
        this.operation = type;
        this.attribute = attr;
    }

    public Type getOperation() {
        return this.operation;
    }

    public LdapAttribute getAttribute() {
        return this.attribute;
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "operation=" + (Object)((Object)this.operation) + ", " + "attribute=" + this.attribute;
    }

    public static enum Type {
        ADD,
        DELETE,
        REPLACE,
        INCREMENT;

    }
}

