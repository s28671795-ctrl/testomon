/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams.validations;

import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.plugin.commons.validation.BeanConstraintValidator;
import com.incoresoft.plugin.commons.validation.SpecifyViolationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;

@Constraint(validatedBy={Validator.class})
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@SpecifyViolationType(value="NOT_BLANK")
public @interface StreamPathMustExistForMilestoneRtsp {
    public String message() default "";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public static class Validator
    extends BeanConstraintValidator<StreamPathMustExistForMilestoneRtsp, Validatable> {
        @Override
        public boolean isValid(Validatable value) {
            if (value.getParams() == null) {
                return true;
            }
            if (StreamType.milestone_rtsp == value.getParams().getType()) {
                return StringUtils.isNotBlank(value.getPath());
            }
            return true;
        }

        @Override
        public String getValidatingFieldName() {
            return "path";
        }
    }

    public static interface Validatable {
        public StreamParams getParams();

        public String getPath();
    }
}

