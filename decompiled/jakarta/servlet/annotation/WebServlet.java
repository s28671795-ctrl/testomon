/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.annotation;

import jakarta.servlet.annotation.WebInitParam;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {
    public String name() default "";

    public String[] value() default {};

    public String[] urlPatterns() default {};

    public int loadOnStartup() default -1;

    public WebInitParam[] initParams() default {};

    public boolean asyncSupported() default false;

    public String smallIcon() default "";

    public String largeIcon() default "";

    public String description() default "";

    public String displayName() default "";
}

