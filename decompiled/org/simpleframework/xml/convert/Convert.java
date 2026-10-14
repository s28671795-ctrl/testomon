/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.simpleframework.xml.convert.Converter;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Convert {
    public Class<? extends Converter> value();
}

