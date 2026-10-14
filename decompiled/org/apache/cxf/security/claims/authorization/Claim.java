/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.security.claims.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.cxf.security.claims.authorization.ClaimMode;

@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Claim {
    public String format() default "urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified";

    public String name() default "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";

    public String[] value();

    public boolean matchAll() default false;

    public ClaimMode mode() default ClaimMode.STRICT;
}

