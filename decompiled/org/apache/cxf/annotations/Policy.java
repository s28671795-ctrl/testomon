/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Policy {
    public String uri();

    public boolean includeInWSDL() default true;

    public Placement placement() default Placement.DEFAULT;

    public Class<?> faultClass() default DEFAULT.class;

    public static final class DEFAULT {
    }

    public static enum Placement {
        DEFAULT,
        PORT_TYPE,
        PORT_TYPE_OPERATION,
        PORT_TYPE_OPERATION_INPUT,
        PORT_TYPE_OPERATION_OUTPUT,
        PORT_TYPE_OPERATION_FAULT,
        BINDING,
        BINDING_OPERATION,
        BINDING_OPERATION_INPUT,
        BINDING_OPERATION_OUTPUT,
        BINDING_OPERATION_FAULT,
        SERVICE,
        SERVICE_PORT;

    }
}

