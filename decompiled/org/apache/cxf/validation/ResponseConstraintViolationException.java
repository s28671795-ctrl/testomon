/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ResponseConstraintViolationException
extends ConstraintViolationException {
    public ResponseConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}

