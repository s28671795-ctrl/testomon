/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.glassfish.hk2.api.ContractIndicator;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@ContractIndicator
public @interface Contract {
}

