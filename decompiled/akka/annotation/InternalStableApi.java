/*
 * Decompiled with CFR 0.152.
 */
package akka.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE, ElementType.PACKAGE})
public @interface InternalStableApi {
}

