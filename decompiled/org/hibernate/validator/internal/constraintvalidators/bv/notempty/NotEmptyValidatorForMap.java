/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.notempty;

import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;

public class NotEmptyValidatorForMap
implements ConstraintValidator<NotEmpty, Map> {
    @Override
    public boolean isValid(Map map, ConstraintValidatorContext constraintValidatorContext) {
        if (map == null) {
            return false;
        }
        return map.size() > 0;
    }
}

