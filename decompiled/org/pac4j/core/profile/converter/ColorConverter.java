/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import org.pac4j.core.profile.Color;
import org.pac4j.core.profile.converter.AbstractAttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ColorConverter
extends AbstractAttributeConverter {
    private static final Logger logger = LoggerFactory.getLogger(ColorConverter.class);

    public ColorConverter() {
        super(Color.class);
    }

    @Override
    protected Color internalConvert(Object attribute) {
        String s2;
        if (attribute instanceof String && (s2 = (String)attribute).length() == 6) {
            try {
                String hex = s2.substring(0, 2);
                int r = Integer.parseInt(hex, 16);
                hex = s2.substring(2, 4);
                int g2 = Integer.parseInt(hex, 16);
                hex = s2.substring(4, 6);
                int b = Integer.parseInt(hex, 16);
                return new Color(r, g2, b);
            }
            catch (NumberFormatException e) {
                logger.error("Cannot convert " + s2 + " into color", e);
            }
        }
        return null;
    }
}

