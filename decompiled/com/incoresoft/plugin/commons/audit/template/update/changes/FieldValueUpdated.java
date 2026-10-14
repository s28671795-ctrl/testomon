/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template.update.changes;

import com.incoresoft.plugin.commons.audit.template.update.changes.FieldChange;

public final class FieldValueUpdated
implements FieldChange {
    private final String fieldName;
    private final String oldValue;
    private final String newValue;

    @Override
    public String getType() {
        return "field_value_updated";
    }

    public FieldValueUpdated(String fieldName, String oldValue, String newValue) {
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    public String getOldValue() {
        return this.oldValue;
    }

    public String getNewValue() {
        return this.newValue;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FieldValueUpdated)) {
            return false;
        }
        FieldValueUpdated other = (FieldValueUpdated)o;
        String this$fieldName = this.getFieldName();
        String other$fieldName = other.getFieldName();
        if (this$fieldName == null ? other$fieldName != null : !this$fieldName.equals(other$fieldName)) {
            return false;
        }
        String this$oldValue = this.getOldValue();
        String other$oldValue = other.getOldValue();
        if (this$oldValue == null ? other$oldValue != null : !this$oldValue.equals(other$oldValue)) {
            return false;
        }
        String this$newValue = this.getNewValue();
        String other$newValue = other.getNewValue();
        return !(this$newValue == null ? other$newValue != null : !this$newValue.equals(other$newValue));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $fieldName = this.getFieldName();
        result = result * 59 + ($fieldName == null ? 43 : $fieldName.hashCode());
        String $oldValue = this.getOldValue();
        result = result * 59 + ($oldValue == null ? 43 : $oldValue.hashCode());
        String $newValue = this.getNewValue();
        result = result * 59 + ($newValue == null ? 43 : $newValue.hashCode());
        return result;
    }

    public String toString() {
        return "FieldValueUpdated(fieldName=" + this.getFieldName() + ", oldValue=" + this.getOldValue() + ", newValue=" + this.getNewValue() + ")";
    }
}

