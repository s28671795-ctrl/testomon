/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class Stringly {

    @Target(value={ElementType.PARAMETER})
    @Retention(value=RetentionPolicy.SOURCE)
    @Documented
    @Inherited
    public static @interface Comment {
    }

    @Target(value={ElementType.PARAMETER})
    @Retention(value=RetentionPolicy.SOURCE)
    @Documented
    @Inherited
    public static @interface Keyword {
    }

    @Target(value={ElementType.PARAMETER})
    @Retention(value=RetentionPolicy.SOURCE)
    @Documented
    @Inherited
    public static @interface Name {
    }

    @Target(value={ElementType.PARAMETER})
    @Retention(value=RetentionPolicy.SOURCE)
    @Documented
    @Inherited
    public static @interface SQL {
    }

    @Target(value={ElementType.PARAMETER})
    @Retention(value=RetentionPolicy.SOURCE)
    @Documented
    @Inherited
    public static @interface Param {
    }
}

