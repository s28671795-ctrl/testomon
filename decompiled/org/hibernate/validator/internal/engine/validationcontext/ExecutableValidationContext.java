/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.validationcontext;

import java.lang.reflect.Executable;
import java.util.Optional;
import org.hibernate.validator.internal.engine.validationcontext.BaseBeanValidationContext;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;

public interface ExecutableValidationContext<T>
extends BaseBeanValidationContext<T> {
    public Executable getExecutable();

    public Optional<ExecutableMetaData> getExecutableMetaData();
}

