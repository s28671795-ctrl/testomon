/*
 * Decompiled with CFR 0.152.
 */
package jakarta.jws.soap;

import jakarta.jws.soap.SOAPMessageHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Deprecated
public @interface SOAPMessageHandlers {
    public SOAPMessageHandler[] value();
}

