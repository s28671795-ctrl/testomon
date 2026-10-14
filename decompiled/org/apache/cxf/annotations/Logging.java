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

@Deprecated
@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface Logging {
    public int limit() default 49152;

    public String inLocation() default "<logger>";

    public String outLocation() default "<logger>";

    public boolean pretty() default false;

    public boolean showBinary() default false;
}

