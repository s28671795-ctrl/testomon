/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class XSBooleanValue {
    private boolean numeric;
    private Boolean value;

    public XSBooleanValue() {
        this.numeric = false;
        this.value = null;
    }

    public XSBooleanValue(@Nullable Boolean newValue, boolean numericRepresentation) {
        this.numeric = numericRepresentation;
        this.value = newValue;
    }

    @Nullable
    public Boolean getValue() {
        return this.value;
    }

    public void setValue(@Nullable Boolean newValue) {
        this.value = newValue;
    }

    public boolean isNumericRepresentation() {
        return this.numeric;
    }

    public void setNumericRepresentation(boolean numericRepresentation) {
        this.numeric = numericRepresentation;
    }

    public int hashCode() {
        int hash = this.numeric ? (this.value == null ? 0 : (this.value.booleanValue() ? 1 : 3)) : (this.value == null ? 4 : (this.value != false ? 5 : 6));
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof XSBooleanValue) {
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }

    public String toString() {
        return XSBooleanValue.toString(this.value, this.numeric);
    }

    public static String toString(Boolean value, boolean numericRepresentation) {
        if (value == null) {
            return "false";
        }
        if (numericRepresentation) {
            if (value.booleanValue()) {
                return "1";
            }
            return "0";
        }
        return value.toString();
    }

    public static XSBooleanValue valueOf(@Nullable String booleanString) {
        String trimmedBooleanString = StringSupport.trimOrNull(booleanString);
        if (trimmedBooleanString != null) {
            if ("1".equals(trimmedBooleanString)) {
                return new XSBooleanValue(Boolean.TRUE, true);
            }
            if ("0".equals(trimmedBooleanString)) {
                return new XSBooleanValue(Boolean.FALSE, true);
            }
            if ("true".equals(trimmedBooleanString)) {
                return new XSBooleanValue(Boolean.TRUE, false);
            }
        }
        return new XSBooleanValue(Boolean.FALSE, false);
    }
}

