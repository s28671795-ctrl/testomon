/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jooq.Attachable;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultOrRows;

public interface Results
extends List<Result<Record>>,
Attachable {
    @NotNull
    public List<ResultOrRows> resultsOrRows();

    @Override
    public void attach(Configuration var1);

    @Override
    public void detach();
}

