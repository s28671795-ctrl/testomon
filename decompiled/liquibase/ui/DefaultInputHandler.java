/*
 * Decompiled with CFR 0.152.
 */
package liquibase.ui;

import liquibase.ui.InputHandler;
import liquibase.util.ObjectUtil;

public class DefaultInputHandler<ReturnType>
implements InputHandler<ReturnType> {
    @Override
    public ReturnType parseInput(String input, Class<ReturnType> returnType) throws IllegalArgumentException {
        try {
            return ObjectUtil.convert(input, returnType);
        }
        catch (IllegalArgumentException e) {
            throw this.addPrefixToExceptionMessage(e, input);
        }
    }

    protected IllegalArgumentException addPrefixToExceptionMessage(IllegalArgumentException ex, String input) {
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            return new IllegalArgumentException(String.format("Invalid value: '%s': %s", input, ex.getCause().getMessage()), ex);
        }
        if (ex.getMessage() != null) {
            return new IllegalArgumentException(String.format("Invalid value: '%s': %s", input, ex.getMessage()), ex);
        }
        return ex;
    }
}

