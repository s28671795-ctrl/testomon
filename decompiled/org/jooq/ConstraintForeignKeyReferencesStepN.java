/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jooq.ConstraintForeignKeyOnStep;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Table;

public interface ConstraintForeignKeyReferencesStepN {
    public ConstraintForeignKeyOnStep references(String var1);

    public ConstraintForeignKeyOnStep references(String var1, String ... var2);

    public ConstraintForeignKeyOnStep references(String var1, Collection<? extends String> var2);

    public ConstraintForeignKeyOnStep references(Name var1);

    public ConstraintForeignKeyOnStep references(Name var1, Name ... var2);

    public ConstraintForeignKeyOnStep references(Name var1, Collection<? extends Name> var2);

    public ConstraintForeignKeyOnStep references(Table<?> var1);

    public ConstraintForeignKeyOnStep references(Table<?> var1, Field<?> ... var2);

    public ConstraintForeignKeyOnStep references(Table<?> var1, Collection<? extends Field<?>> var2);
}

