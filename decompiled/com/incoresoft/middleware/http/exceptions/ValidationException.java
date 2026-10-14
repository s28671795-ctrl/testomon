/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.exceptions;

import com.incoresoft.middleware.http.errors.Error;
import com.incoresoft.middleware.validation.ValidationViolation;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class ValidationException
extends RuntimeException {
    private final List<ValidationViolation> violations;

    public ValidationException(@NotNull List<ValidationViolation> violations) {
        this.violations = Objects.requireNonNull(violations);
    }

    public ValidationException(String type, String field2, Object ... args2) {
        this(List.of(new ValidationViolation(type, field2, args2)));
    }

    public ValidationException(Error error, String field2, Object ... args2) {
        this(List.of(new ValidationViolation(error.getLocaleKey(), field2, args2)));
    }

    public List<ValidationViolation> getViolations() {
        return this.violations;
    }
}

