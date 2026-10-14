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
@Target(value={ElementType.TYPE})
@Inherited
public @interface FastInfoset {
    public boolean force() default false;

    public int serializerAttributeValueMapMemoryLimit() default -1;

    public int serializerMinAttributeValueSize() default -1;

    public int serializerMaxAttributeValueSize() default -1;

    public int serializerCharacterContentChunkMapMemoryLimit() default -1;

    public int serializerMinCharacterContentChunkSize() default -1;

    public int serializerMaxCharacterContentChunkSize() default -1;
}

