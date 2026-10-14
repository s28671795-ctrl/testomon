/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.simpleframework.xml.transform.AtomicIntegerTransform;
import org.simpleframework.xml.transform.AtomicLongTransform;
import org.simpleframework.xml.transform.BigDecimalTransform;
import org.simpleframework.xml.transform.BigIntegerTransform;
import org.simpleframework.xml.transform.BooleanTransform;
import org.simpleframework.xml.transform.ByteTransform;
import org.simpleframework.xml.transform.CharacterTransform;
import org.simpleframework.xml.transform.ClassTransform;
import org.simpleframework.xml.transform.CurrencyTransform;
import org.simpleframework.xml.transform.DateTransform;
import org.simpleframework.xml.transform.DoubleTransform;
import org.simpleframework.xml.transform.EnumTransform;
import org.simpleframework.xml.transform.FileTransform;
import org.simpleframework.xml.transform.FloatTransform;
import org.simpleframework.xml.transform.GregorianCalendarTransform;
import org.simpleframework.xml.transform.IntegerTransform;
import org.simpleframework.xml.transform.LocaleTransform;
import org.simpleframework.xml.transform.LongTransform;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.ShortTransform;
import org.simpleframework.xml.transform.StringTransform;
import org.simpleframework.xml.transform.TimeZoneTransform;
import org.simpleframework.xml.transform.Transform;
import org.simpleframework.xml.transform.URLTransform;

class PackageMatcher
implements Matcher {
    @Override
    public Transform match(Class type) throws Exception {
        String name = type.getName();
        if (name.startsWith("java.lang")) {
            return this.matchLanguage(type);
        }
        if (name.startsWith("java.util")) {
            return this.matchUtility(type);
        }
        if (name.startsWith("java.net")) {
            return this.matchURL(type);
        }
        if (name.startsWith("java.io")) {
            return this.matchFile(type);
        }
        if (name.startsWith("java.sql")) {
            return this.matchSQL(type);
        }
        if (name.startsWith("java.math")) {
            return this.matchMath(type);
        }
        return this.matchEnum(type);
    }

    private Transform matchEnum(Class type) {
        Class parent = type.getSuperclass();
        if (parent != null) {
            if (parent.isEnum()) {
                return new EnumTransform(type);
            }
            if (type.isEnum()) {
                return new EnumTransform(type);
            }
        }
        return null;
    }

    private Transform matchLanguage(Class type) throws Exception {
        if (type == Boolean.class) {
            return new BooleanTransform();
        }
        if (type == Integer.class) {
            return new IntegerTransform();
        }
        if (type == Long.class) {
            return new LongTransform();
        }
        if (type == Double.class) {
            return new DoubleTransform();
        }
        if (type == Float.class) {
            return new FloatTransform();
        }
        if (type == Short.class) {
            return new ShortTransform();
        }
        if (type == Byte.class) {
            return new ByteTransform();
        }
        if (type == Character.class) {
            return new CharacterTransform();
        }
        if (type == String.class) {
            return new StringTransform();
        }
        if (type == Class.class) {
            return new ClassTransform();
        }
        return null;
    }

    private Transform matchMath(Class type) throws Exception {
        if (type == BigDecimal.class) {
            return new BigDecimalTransform();
        }
        if (type == BigInteger.class) {
            return new BigIntegerTransform();
        }
        return null;
    }

    private Transform matchUtility(Class type) throws Exception {
        if (type == java.util.Date.class) {
            return new DateTransform(type);
        }
        if (type == Locale.class) {
            return new LocaleTransform();
        }
        if (type == Currency.class) {
            return new CurrencyTransform();
        }
        if (type == GregorianCalendar.class) {
            return new GregorianCalendarTransform();
        }
        if (type == TimeZone.class) {
            return new TimeZoneTransform();
        }
        if (type == AtomicInteger.class) {
            return new AtomicIntegerTransform();
        }
        if (type == AtomicLong.class) {
            return new AtomicLongTransform();
        }
        return null;
    }

    private Transform matchSQL(Class type) throws Exception {
        if (type == Time.class) {
            return new DateTransform(type);
        }
        if (type == Date.class) {
            return new DateTransform(type);
        }
        if (type == Timestamp.class) {
            return new DateTransform(type);
        }
        return null;
    }

    private Transform matchFile(Class type) throws Exception {
        if (type == File.class) {
            return new FileTransform();
        }
        return null;
    }

    private Transform matchURL(Class type) throws Exception {
        if (type == URL.class) {
            return new URLTransform();
        }
        return null;
    }
}

