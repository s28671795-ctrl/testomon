/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.bv;

import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class EmailValidator
extends AbstractEmailValidator<Email> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private Pattern pattern;

    @Override
    public void initialize(Email emailAnnotation) {
        super.initialize(emailAnnotation);
        Pattern.Flag[] flags = emailAnnotation.flags();
        int intFlag = 0;
        for (Pattern.Flag flag : flags) {
            intFlag |= flag.getValue();
        }
        if (!".*".equals(emailAnnotation.regexp()) || emailAnnotation.flags().length > 0) {
            try {
                this.pattern = Pattern.compile(emailAnnotation.regexp(), intFlag);
            }
            catch (PatternSyntaxException e) {
                throw LOG.getInvalidRegularExpressionException(e);
            }
        }
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean isValid = super.isValid(value, context);
        if (this.pattern == null || !isValid) {
            return isValid;
        }
        Matcher m4 = this.pattern.matcher(value);
        return m4.matches();
    }
}

