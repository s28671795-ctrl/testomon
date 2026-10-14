/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.validation;

import java.util.Arrays;

public final class ValidationViolation {
    private final String type;
    private final String field;
    private final Object[] args;

    public ValidationViolation(String type, String field2, Object ... args2) {
        this.type = type;
        this.field = field2;
        this.args = args2;
    }

    public String getType() {
        return this.type;
    }

    public String getField() {
        return this.field;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ValidationViolation)) {
            return false;
        }
        ValidationViolation other = (ValidationViolation)o;
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        String this$field = this.getField();
        String other$field = other.getField();
        if (this$field == null ? other$field != null : !this$field.equals(other$field)) {
            return false;
        }
        return Arrays.deepEquals(this.getArgs(), other.getArgs());
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        String $field = this.getField();
        result = result * 59 + ($field == null ? 43 : $field.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getArgs());
        return result;
    }

    public String toString() {
        return "ValidationViolation(type=" + this.getType() + ", field=" + this.getField() + ", args=" + Arrays.deepToString(this.getArgs()) + ")";
    }
}

