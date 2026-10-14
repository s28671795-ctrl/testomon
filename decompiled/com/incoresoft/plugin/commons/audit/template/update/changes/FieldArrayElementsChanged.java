/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template.update.changes;

import com.incoresoft.plugin.commons.audit.template.update.changes.FieldChange;

public final class FieldArrayElementsChanged
implements FieldChange {
    private final String fieldName;

    @Override
    public String getType() {
        return "field_array_elements_changed";
    }

    public FieldArrayElementsChanged(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FieldArrayElementsChanged)) {
            return false;
        }
        FieldArrayElementsChanged other = (FieldArrayElementsChanged)o;
        String this$fieldName = this.getFieldName();
        String other$fieldName = other.getFieldName();
        return !(this$fieldName == null ? other$fieldName != null : !this$fieldName.equals(other$fieldName));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $fieldName = this.getFieldName();
        result = result * 59 + ($fieldName == null ? 43 : $fieldName.hashCode());
        return result;
    }

    public String toString() {
        return "FieldArrayElementsChanged(fieldName=" + this.getFieldName() + ")";
    }
}

