/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.spi.ErrorDetail;
import java.io.Serializable;
import java.util.Formatter;
import java.util.List;

public final class GenericErrorDetail
extends InternalErrorDetail<GenericErrorDetail>
implements Serializable {
    public GenericErrorDetail(ErrorId errorId, String message, List<Object> sources, Throwable cause) {
        super(errorId, Preconditions.checkNotNull(message, "message"), sources, cause);
    }

    @Override
    public void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter) {
        Preconditions.checkArgument(mergeableErrors.isEmpty(), "Unexpected mergeable errors");
        List<Object> dependencies = this.getSources();
        for (Object source2 : Lists.reverse(dependencies)) {
            formatter.format("  ", new Object[0]);
            new SourceFormatter(source2, formatter, false).format();
        }
    }

    @Override
    public GenericErrorDetail withSources(List<Object> newSources) {
        return new GenericErrorDetail(this.errorId, this.getMessage(), newSources, this.getCause());
    }
}

