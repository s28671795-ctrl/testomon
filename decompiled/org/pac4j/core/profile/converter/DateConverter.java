/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import org.pac4j.core.profile.converter.AbstractAttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateConverter
extends AbstractAttributeConverter {
    protected static final Logger logger = LoggerFactory.getLogger(DateConverter.class);
    protected String format;
    protected Locale locale;

    public DateConverter() {
        this(DateTimeFormatter.ISO_LOCAL_DATE_TIME.toString());
    }

    public DateConverter(String format) {
        super(Date.class);
        this.format = format;
    }

    public DateConverter(String format, Locale locale) {
        this(format);
        this.locale = locale;
    }

    @Override
    protected Date internalConvert(Object attribute) {
        if (attribute instanceof String) {
            SimpleDateFormat simpleDateFormat = this.locale == null ? new SimpleDateFormat(this.format) : new SimpleDateFormat(this.format, this.locale);
            String s2 = (String)attribute;
            try {
                return simpleDateFormat.parse(s2);
            }
            catch (ParseException e) {
                logger.error("parse exception on {} with format: {} and locale: {}", s2, this.format, this.locale, e);
            }
        }
        return null;
    }
}

