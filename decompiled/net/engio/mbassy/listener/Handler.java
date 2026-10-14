/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.engio.mbassy.dispatch.HandlerInvocation;
import net.engio.mbassy.dispatch.ReflectiveHandlerInvocation;
import net.engio.mbassy.listener.Filter;
import net.engio.mbassy.listener.Invoke;

@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
@Target(value={ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Handler {
    public Filter[] filters() default {};

    public String condition() default "";

    public Invoke delivery() default Invoke.Synchronously;

    public int priority() default 0;

    public boolean rejectSubtypes() default false;

    public boolean enabled() default true;

    public Class<? extends HandlerInvocation> invocation() default ReflectiveHandlerInvocation.class;
}

