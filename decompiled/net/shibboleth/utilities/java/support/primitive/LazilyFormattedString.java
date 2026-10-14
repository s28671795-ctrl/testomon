/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.primitive;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class LazilyFormattedString {
    private final String template;
    private final Object[] arguments;

    public LazilyFormattedString(@Nonnull String stringTemplate, Object ... templateArguments) {
        this.template = Constraint.isNotNull(stringTemplate, "String template can not be null");
        this.arguments = templateArguments;
    }

    public String toString() {
        return String.format(this.template, this.arguments);
    }
}

