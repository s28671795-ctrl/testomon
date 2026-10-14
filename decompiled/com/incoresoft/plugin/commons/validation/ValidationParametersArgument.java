/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.validation;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Map;

public final class ValidationParametersArgument {
    @JSONField(name="validation_parameters")
    private final Map<String, Object> validationParameters;

    public ValidationParametersArgument(Map<String, Object> validationParameters) {
        this.validationParameters = validationParameters;
    }

    public Map<String, Object> getValidationParameters() {
        return this.validationParameters;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ValidationParametersArgument)) {
            return false;
        }
        ValidationParametersArgument other = (ValidationParametersArgument)o;
        Map<String, Object> this$validationParameters = this.getValidationParameters();
        Map<String, Object> other$validationParameters = other.getValidationParameters();
        return !(this$validationParameters == null ? other$validationParameters != null : !((Object)this$validationParameters).equals(other$validationParameters));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Map<String, Object> $validationParameters = this.getValidationParameters();
        result = result * 59 + ($validationParameters == null ? 43 : ((Object)$validationParameters).hashCode());
        return result;
    }

    public String toString() {
        return "ValidationParametersArgument(validationParameters=" + String.valueOf(this.getValidationParameters()) + ")";
    }
}

