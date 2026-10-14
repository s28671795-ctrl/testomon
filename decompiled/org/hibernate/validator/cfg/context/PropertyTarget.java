/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.cfg.context;

import java.lang.annotation.ElementType;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;

public interface PropertyTarget {
    @Deprecated
    public PropertyConstraintMappingContext property(String var1, ElementType var2);

    public PropertyConstraintMappingContext field(String var1);

    public PropertyConstraintMappingContext getter(String var1);
}

