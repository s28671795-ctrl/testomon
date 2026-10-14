/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.impl.FieldsImpl;

final class Intern
implements Serializable {
    int[] internIndexes;
    Field<?>[] internFields;
    String[] internNameStrings;
    Name[] internNames;

    Intern() {
    }

    final int[] internIndexes(Field<?>[] fields) {
        if (this.internIndexes != null) {
            return this.internIndexes;
        }
        if (this.internFields != null) {
            return new FieldsImpl(fields).indexesOf(this.internFields);
        }
        if (this.internNameStrings != null) {
            return new FieldsImpl(fields).indexesOf(this.internNameStrings);
        }
        if (this.internNames != null) {
            return new FieldsImpl(fields).indexesOf(this.internNames);
        }
        return null;
    }
}

