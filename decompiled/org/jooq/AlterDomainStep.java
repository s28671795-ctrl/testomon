/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.AlterDomainDropConstraintCascadeStep;
import org.jooq.AlterDomainFinalStep;
import org.jooq.AlterDomainRenameConstraintStep;
import org.jooq.CheckReturnValue;
import org.jooq.Constraint;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface AlterDomainStep<T> {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep add(Constraint var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainDropConstraintCascadeStep dropConstraint(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainDropConstraintCascadeStep dropConstraint(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainDropConstraintCascadeStep dropConstraint(Constraint var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainDropConstraintCascadeStep dropConstraintIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainDropConstraintCascadeStep dropConstraintIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainDropConstraintCascadeStep dropConstraintIfExists(Constraint var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep renameTo(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep renameTo(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep renameTo(Domain<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainRenameConstraintStep renameConstraint(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainRenameConstraintStep renameConstraint(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainRenameConstraintStep renameConstraint(Constraint var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainRenameConstraintStep renameConstraintIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainRenameConstraintStep renameConstraintIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainRenameConstraintStep renameConstraintIfExists(Constraint var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep setDefault(T var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep setDefault(Field<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep dropDefault();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep setNotNull();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterDomainFinalStep dropNotNull();
}

