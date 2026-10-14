/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.ConstraintFinalStep;
import org.jooq.SQLDialect;
import org.jooq.Support;

public interface ConstraintEnforcementStep
extends ConstraintFinalStep {
    @Support(value={SQLDialect.DB2, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE})
    @NotNull
    public ConstraintFinalStep enforced();

    @Support(value={SQLDialect.DB2, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE})
    @NotNull
    public ConstraintFinalStep notEnforced();
}

