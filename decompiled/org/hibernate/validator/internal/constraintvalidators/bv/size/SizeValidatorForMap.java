/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv.size;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class SizeValidatorForMap
implements ConstraintValidator<Size, Map> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private int min;
    private int max;

    @Override
    public void initialize(Size parameters) {
        this.min = parameters.min();
        this.max = parameters.max();
        this.validateParameters();
    }

    @Override
    public boolean isValid(Map map, ConstraintValidatorContext constraintValidatorContext) {
        if (map == null) {
            return true;
        }
        int size = map.size();
        return size >= this.min && size <= this.max;
    }

    private void validateParameters() {
        if (this.min < 0) {
            throw LOG.getMaxCannotBeNegativeException();
        }
        if (this.max < 0) {
            throw LOG.getMaxCannotBeNegativeException();
        }
        if (this.max < this.min) {
            throw LOG.getLengthCannotBeNegativeException();
        }
    }
}

