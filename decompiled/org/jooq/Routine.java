/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Attachable;
import org.jooq.Configuration;
import org.jooq.Package;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.Qualified;
import org.jooq.Results;
import org.jooq.exception.DataAccessException;

public interface Routine<T>
extends Qualified,
Attachable {
    @Nullable
    @Pro
    public Package getPackage();

    @Nullable
    public Parameter<T> getReturnParameter();

    @NotNull
    public List<Parameter<?>> getOutParameters();

    @NotNull
    public List<Parameter<?>> getInParameters();

    @NotNull
    public List<Parameter<?>> getParameters();

    @Blocking
    public int execute(Configuration var1) throws DataAccessException;

    @Blocking
    public int execute() throws DataAccessException;

    public <Z> void setValue(Parameter<Z> var1, Z var2);

    public <Z> void set(Parameter<Z> var1, Z var2);

    public <Z> Z getValue(Parameter<Z> var1);

    public <Z> Z getInValue(Parameter<Z> var1);

    public <Z> Z get(Parameter<Z> var1);

    @Nullable
    public T getReturnValue();

    @NotNull
    public Results getResults();
}

