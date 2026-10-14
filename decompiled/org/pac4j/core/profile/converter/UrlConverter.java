/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import java.net.URI;
import org.pac4j.core.profile.converter.AbstractAttributeConverter;
import org.pac4j.core.util.CommonHelper;

public class UrlConverter
extends AbstractAttributeConverter {
    public UrlConverter() {
        super(URI.class);
    }

    @Override
    protected URI internalConvert(Object attribute) {
        if (attribute instanceof String) {
            String s2 = ((String)attribute).replaceAll("\\/", "/");
            return CommonHelper.asURI(s2);
        }
        return null;
    }
}

