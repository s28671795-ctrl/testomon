/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Locale;
import org.h2.message.DbException;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;
import org.h2.value.CompareMode;

public class CompareModeIcu4J
extends CompareMode {
    private final Comparator<String> collator;
    private volatile CompareModeIcu4J caseInsensitive;

    protected CompareModeIcu4J(String string, int n) {
        super(string, n);
        this.collator = CompareModeIcu4J.getIcu4jCollator(string, n);
    }

    @Override
    public int compareString(String string, String string2, boolean bl) {
        if (bl && this.getStrength() > 1) {
            CompareModeIcu4J compareModeIcu4J = this.caseInsensitive;
            if (compareModeIcu4J == null) {
                this.caseInsensitive = compareModeIcu4J = new CompareModeIcu4J(this.getName(), 1);
            }
            return compareModeIcu4J.compareString(string, string2, false);
        }
        return this.collator.compare(string, string2);
    }

    @Override
    public boolean equalsChars(String string, int n, String string2, int n2, boolean bl) {
        return this.compareString(string.substring(n, n + 1), string2.substring(n2, n2 + 1), bl) == 0;
    }

    private static Comparator<String> getIcu4jCollator(String string, int n) {
        try {
            String string2;
            String string3;
            Locale locale2;
            int n2;
            Comparator comparator = null;
            Class clazz = JdbcUtils.loadUserClass("com.ibm.icu.text.Collator");
            Method method2 = clazz.getMethod("getInstance", Locale.class);
            int n3 = string.length();
            if (n3 == 2) {
                Locale[] localeArray = new Locale(StringUtils.toLowerEnglish(string), "");
                if (CompareModeIcu4J.compareLocaleNames((Locale)localeArray, string)) {
                    comparator = (Comparator)method2.invoke(null, new Object[]{localeArray});
                }
            } else if (n3 == 5 && (n2 = string.indexOf(95)) >= 0 && CompareModeIcu4J.compareLocaleNames(locale2 = new Locale(string3 = StringUtils.toLowerEnglish(string.substring(0, n2)), string2 = string.substring(n2 + 1)), string)) {
                comparator = (Comparator)method2.invoke(null, locale2);
            }
            if (comparator == null) {
                for (Locale locale2 : (Locale[])clazz.getMethod("getAvailableLocales", new Class[0]).invoke(null, new Object[0])) {
                    if (!CompareModeIcu4J.compareLocaleNames(locale2, string)) continue;
                    comparator = (Comparator)method2.invoke(null, locale2);
                    break;
                }
            }
            if (comparator == null) {
                throw DbException.getInvalidValueException("collator", string);
            }
            clazz.getMethod("setStrength", Integer.TYPE).invoke((Object)comparator, n);
            return comparator;
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
    }
}

