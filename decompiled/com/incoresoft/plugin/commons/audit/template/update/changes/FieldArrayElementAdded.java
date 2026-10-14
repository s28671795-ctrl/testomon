/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template.update.changes;

import com.incoresoft.plugin.commons.audit.template.update.changes.FieldChange;

public final class FieldArrayElementAdded
implements FieldChange {
    private final String fieldName;
    private final String value;

    @Override
    public String getType() {
        return "field_array_element_added";
    }

    public FieldArrayElementAdded(String fieldName, String value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FieldArrayElementAdded)) {
            return false;
        }
        FieldArrayElementAdded other = (FieldArrayElementAdded)o;
        String this$fieldName = this.getFieldName();
        String other$fieldName = other.getFieldName();
        if (this$fieldName == null ? other$fieldName != null : !this$fieldName.equals(other$fieldName)) {
            return false;
        }
        String this$value = this.getValue();
        String other$value = other.getValue();
        return !(this$value == null ? other$value != null : !this$value.equals(other$value));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $fieldName = this.getFieldName();
        result = result * 59 + ($fieldName == null ? 43 : $fieldName.hashCode());
        String $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        return result;
    }

    public String toString() {
        return "FieldArrayElementAdded(fieldName=" + this.getFieldName() + ", value=" + this.getValue() + ")";
    }
}

