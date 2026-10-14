/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.engio.mbassy.listener.IMessageFilter;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface Filter {
    public Class<? extends IMessageFilter> value();
}

