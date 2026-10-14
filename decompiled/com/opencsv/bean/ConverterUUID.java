/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class ConverterUUID
extends AbstractCsvConverter {
    private static final String UUID_REGEX_PATTERN = "\\b[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-\\b[0-9a-fA-F]{12}\\b";

    public ConverterUUID(Locale errorLocale) {
        super(UUID.class, null, null, errorLocale);
    }

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        String trimmedString = value.trim();
        if (!Pattern.matches(UUID_REGEX_PATTERN, trimmedString)) {
            throw new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv").getString("invalid.uuid.value"), value, this.type.getName()));
        }
        return UUID.fromString(trimmedString);
    }
}

