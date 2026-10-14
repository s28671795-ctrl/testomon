/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jooq.Condition;
import org.jooq.Operator;
import org.jooq.Support;

@Deprecated(forRemoval=true, since="2.6")
public interface ConditionProvider {
    @Support
    public void addConditions(Condition var1);

    @Support
    public void addConditions(Condition ... var1);

    @Support
    public void addConditions(Collection<? extends Condition> var1);

    @Support
    public void addConditions(Operator var1, Condition var2);

    @Support
    public void addConditions(Operator var1, Condition ... var2);

    @Support
    public void addConditions(Operator var1, Collection<? extends Condition> var2);
}

