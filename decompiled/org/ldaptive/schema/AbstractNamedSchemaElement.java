/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import org.ldaptive.schema.AbstractSchemaElement;

public abstract class AbstractNamedSchemaElement
extends AbstractSchemaElement {
    private String[] names;
    private boolean obsolete;

    public String getName() {
        if (this.names != null && this.names.length > 0) {
            return this.names[0];
        }
        return null;
    }

    public String[] getNames() {
        return this.names;
    }

    public void setNames(String[] s2) {
        this.names = s2;
    }

    public boolean hasName(String s2) {
        if (this.names != null) {
            for (String name : this.names) {
                if (!name.equalsIgnoreCase(s2)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isObsolete() {
        return this.obsolete;
    }

    public void setObsolete(boolean b) {
        this.obsolete = b;
    }
}

