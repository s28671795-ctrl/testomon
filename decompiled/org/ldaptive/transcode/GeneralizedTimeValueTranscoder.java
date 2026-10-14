/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.transcode.AbstractStringValueTranscoder;

public class GeneralizedTimeValueTranscoder
extends AbstractStringValueTranscoder<ZonedDateTime> {
    private static final String YEAR_PATTERN = "(\\d{4})";
    private static final String MONTH_PATTERN = "((?:\\x30[\\x31-\\x39])|(?:\\x31[\\x30-\\x32]))";
    private static final String DAY_PATTERN = "((?:\\x30[\\x31-\\x39])|(?:[\\x31-\\x32][\\x30-\\x39])|(?:\\x33[\\x30-\\x31]))";
    private static final String HOUR_PATTERN = "((?:[\\x30-\\x31][\\x30-\\x39])|(?:\\x32[\\x30-\\x33]))";
    private static final String MIN_PATTERN = "([\\x30-\\x35][\\x30-\\x39])?";
    private static final String SECOND_PATTERN = "([\\x30-\\x35][\\x30-\\x39])?";
    private static final String FRACTION_PATTERN = "([,.](\\d+))?";
    private static final String TIMEZONE_PATTERN = "(Z|(?:[+-]((?:[\\x30-\\x31][\\x30-\\x39])|(?:\\x32[\\x30-\\x33]))([\\x30-\\x35][\\x30-\\x39])?))";
    private static final Pattern TIME_REGEX = Pattern.compile("(\\d{4})((?:\\x30[\\x31-\\x39])|(?:\\x31[\\x30-\\x32]))((?:\\x30[\\x31-\\x39])|(?:[\\x31-\\x32][\\x30-\\x39])|(?:\\x33[\\x30-\\x31]))((?:[\\x30-\\x31][\\x30-\\x39])|(?:\\x32[\\x30-\\x33]))([\\x30-\\x35][\\x30-\\x39])?([\\x30-\\x35][\\x30-\\x39])?([,.](\\d+))?(Z|(?:[+-]((?:[\\x30-\\x31][\\x30-\\x39])|(?:\\x32[\\x30-\\x33]))([\\x30-\\x35][\\x30-\\x39])?))");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS'Z'");

    @Override
    public ZonedDateTime decodeStringValue(String value) {
        try {
            return this.parseGeneralizedTime(value);
        }
        catch (ParseException | DateTimeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String encodeStringValue(ZonedDateTime value) {
        if (value.getZone().normalized().equals(ZoneOffset.UTC)) {
            return value.format(DATE_FORMAT);
        }
        return value.withZoneSameInstant(ZoneOffset.UTC).format(DATE_FORMAT);
    }

    @Override
    public Class<ZonedDateTime> getType() {
        return ZonedDateTime.class;
    }

    protected ZonedDateTime parseGeneralizedTime(String value) throws ParseException {
        if (value == null) {
            throw new IllegalArgumentException("String to parse cannot be null.");
        }
        Matcher m4 = TIME_REGEX.matcher(value);
        if (!m4.matches()) {
            throw new ParseException("Invalid generalized time string.", value.length());
        }
        String tzString = m4.group(9);
        ZoneId zoneId = "Z".equals(tzString) ? ZoneOffset.UTC : ZoneId.of("GMT" + tzString);
        int year = Integer.parseInt(m4.group(1));
        int month = Integer.parseInt(m4.group(2));
        int dayOfMonth = Integer.parseInt(m4.group(3));
        int hour = Integer.parseInt(m4.group(4));
        FractionalPart fraction = FractionalPart.Hours;
        int minutes = 0;
        if (m4.group(5) != null) {
            fraction = FractionalPart.Minutes;
            minutes = Integer.parseInt(m4.group(5));
        }
        int seconds = 0;
        if (m4.group(6) != null) {
            fraction = FractionalPart.Seconds;
            seconds = Integer.parseInt(m4.group(6));
        }
        int millis = 0;
        if (m4.group(7) != null) {
            millis = fraction.toMillis(m4.group(8));
        }
        return ZonedDateTime.of(LocalDateTime.of(year, month, dayOfMonth, hour, minutes, seconds).plus(millis, ChronoUnit.MILLIS), zoneId);
    }

    private static enum FractionalPart {
        Hours(3600000),
        Minutes(60000),
        Seconds(1000);

        private final int scaleFactor;

        private FractionalPart(int scale) {
            this.scaleFactor = scale;
        }

        int toMillis(String fraction) {
            return (int)(Double.parseDouble("." + fraction) * (double)this.scaleFactor);
        }
    }
}

