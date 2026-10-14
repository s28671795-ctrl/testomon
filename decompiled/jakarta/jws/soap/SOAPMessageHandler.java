/*
 * Decompiled with CFR 0.152.
 */
package jakarta.jws.soap;

import jakarta.jws.soap.InitParam;

@Deprecated
public @interface SOAPMessageHandler {
    public String name() default "";

    public String className();

    public InitParam[] initParams() default {};

    public String[] roles() default {};

    public String[] headers() default {};
}

