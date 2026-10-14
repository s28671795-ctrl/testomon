/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff;

import liquibase.serializer.AbstractLiquibaseSerializable;
import liquibase.serializer.LiquibaseSerializable;

public class Difference
extends AbstractLiquibaseSerializable
implements Comparable,
LiquibaseSerializable {
    private final String message;
    private final String field;
    private final Object referenceValue;
    private final Object comparedValue;

    public Difference(String field2, Object referenceValue, Object comparedValue) {
        this(null, field2, referenceValue, comparedValue);
    }

    public Difference(String message, String field2, Object referenceValue, Object comparedValue) {
        if (message == null) {
            message = field2 + " changed from '" + referenceValue + "' to '" + comparedValue + "'";
        }
        this.message = message;
        this.field = field2;
        this.referenceValue = referenceValue;
        this.comparedValue = comparedValue;
    }

    @Override
    public String getSerializedObjectName() {
        return "difference";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return null;
    }

    public String getMessage() {
        return this.message;
    }

    public String getField() {
        return this.field;
    }

    public Object getReferenceValue() {
        return this.referenceValue;
    }

    public Object getComparedValue() {
        return this.comparedValue;
    }

    public String toString() {
        return this.message;
    }

    public int compareTo(Object o) {
        return this.getField().compareTo(((Difference)o).getField());
    }
}

