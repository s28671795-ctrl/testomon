/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.security.claims.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.cxf.security.claims.authorization.Claim;

@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Claims {
    public String realm() default "";

    public Claim[] value();
}

