/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.notempty;

import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;

public class NotEmptyValidatorForCollection
implements ConstraintValidator<NotEmpty, Collection> {
    @Override
    public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
        if (collection == null) {
            return false;
        }
        return collection.size() > 0;
    }
}

