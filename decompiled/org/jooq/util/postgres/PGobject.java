/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.postgres;

import java.io.Serializable;
import java.sql.SQLException;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PGobject
implements Serializable,
Cloneable {
    protected String type;
    protected String value;

    public final void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) throws SQLException {
        this.value = value;
    }

    public final String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isNull() {
        return this.getValue() != null;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PGobject) {
            String otherValue = ((PGobject)obj).getValue();
            if (otherValue == null) {
                return this.getValue() == null;
            }
            return otherValue.equals(this.getValue());
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return this.getValue();
    }

    public int hashCode() {
        String value = this.getValue();
        return value != null ? value.hashCode() : 0;
    }

    protected static boolean equals(Object a, Object b) {
        return a == b || a != null && a.equals(b);
    }
}

