/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;

public class ConstraintCreationContext {
    private final ConstraintHelper constraintHelper;
    private final ConstraintValidatorManager constraintValidatorManager;
    private final TypeResolutionHelper typeResolutionHelper;
    private final ValueExtractorManager valueExtractorManager;

    public ConstraintCreationContext(ConstraintHelper constraintHelper, ConstraintValidatorManager constraintValidatorManager, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
        this.constraintHelper = constraintHelper;
        this.constraintValidatorManager = constraintValidatorManager;
        this.typeResolutionHelper = typeResolutionHelper;
        this.valueExtractorManager = valueExtractorManager;
    }

    public ConstraintHelper getConstraintHelper() {
        return this.constraintHelper;
    }

    public ConstraintValidatorManager getConstraintValidatorManager() {
        return this.constraintValidatorManager;
    }

    public TypeResolutionHelper getTypeResolutionHelper() {
        return this.typeResolutionHelper;
    }

    public ValueExtractorManager getValueExtractorManager() {
        return this.valueExtractorManager;
    }
}

