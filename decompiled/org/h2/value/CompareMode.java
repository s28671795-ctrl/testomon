/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.nio.charset.Charset;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import org.h2.util.StringUtils;
import org.h2.value.CharsetCollator;
import org.h2.value.CompareModeDefault;
import org.h2.value.CompareModeIcu4J;
import org.h2.value.Value;

public class CompareMode
implements Comparator<Value> {
    public static final String OFF = "OFF";
    public static final String DEFAULT = "DEFAULT_";
    public static final String ICU4J = "ICU4J_";
    public static final String CHARSET = "CHARSET_";
    private static Locale[] LOCALES;
    private static volatile CompareMode lastUsed;
    private static final boolean CAN_USE_ICU4J;
    private final String name;
    private final int strength;

    protected CompareMode(String string, int n) {
        this.name = string;
        this.strength = n;
    }

    public static CompareMode getInstance(String string, int n) {
        CompareMode compareMode = lastUsed;
        if (compareMode != null && Objects.equals(compareMode.name, string) && compareMode.strength == n) {
            return compareMode;
        }
        if (string == null || string.equals(OFF)) {
            compareMode = new CompareMode(string, n);
        } else {
            boolean bl;
            if (string.startsWith(ICU4J)) {
                bl = true;
                string = string.substring(ICU4J.length());
            } else if (string.startsWith(DEFAULT)) {
                bl = false;
                string = string.substring(DEFAULT.length());
            } else {
                bl = string.startsWith(CHARSET) ? false : CAN_USE_ICU4J;
            }
            compareMode = bl ? new CompareModeIcu4J(string, n) : new CompareModeDefault(string, n);
        }
        lastUsed = compareMode;
        return compareMode;
    }

    public static Locale[] getCollationLocales(boolean bl) {
        Locale[] localeArray = LOCALES;
        if (localeArray == null && !bl) {
            localeArray = Collator.getAvailableLocales();
            LOCALES = localeArray;
        }
        return localeArray;
    }

    public boolean equalsChars(String string, int n, String string2, int n2, boolean bl) {
        char c;
        char c2 = string.charAt(n);
        if (c2 == (c = string2.charAt(n2))) {
            return true;
        }
        return bl && (Character.toUpperCase(c2) == Character.toUpperCase(c) || Character.toLowerCase(c2) == Character.toLowerCase(c));
    }

    public int compareString(String string, String string2, boolean bl) {
        if (bl) {
            return string.compareToIgnoreCase(string2);
        }
        return string.compareTo(string2);
    }

    public static String getName(Locale locale) {
        Locale locale2 = Locale.ENGLISH;
        String string = locale.getDisplayLanguage(locale2) + ' ' + locale.getDisplayCountry(locale2) + ' ' + locale.getVariant();
        string = StringUtils.toUpperEnglish(string.trim().replace(' ', '_'));
        return string;
    }

    static boolean compareLocaleNames(Locale locale, String string) {
        return string.equalsIgnoreCase(locale.toString()) || string.equalsIgnoreCase(locale.toLanguageTag()) || string.equalsIgnoreCase(CompareMode.getName(locale));
    }

    public static Collator getCollator(String string) {
        Locale[] localeArray;
        Collator collator = null;
        if (string.startsWith(ICU4J)) {
            string = string.substring(ICU4J.length());
        } else if (string.startsWith(DEFAULT)) {
            string = string.substring(DEFAULT.length());
        } else if (string.startsWith(CHARSET)) {
            return new CharsetCollator(Charset.forName(string.substring(CHARSET.length())));
        }
        int n = string.length();
        if (n == 2) {
            localeArray = new Locale(StringUtils.toLowerEnglish(string), "");
            if (CompareMode.compareLocaleNames((Locale)localeArray, string)) {
                collator = Collator.getInstance((Locale)localeArray);
            }
        } else if (n == 5) {
            String string2;
            String string3;
            Locale locale;
            int n2 = string.indexOf(95);
            if (n2 >= 0 && CompareMode.compareLocaleNames(locale = new Locale(string3 = StringUtils.toLowerEnglish(string.substring(0, n2)), string2 = string.substring(n2 + 1)), string)) {
                collator = Collator.getInstance(locale);
            }
        } else if (string.indexOf(45) > 0 && !(localeArray = Locale.forLanguageTag(string)).getLanguage().isEmpty()) {
            return Collator.getInstance((Locale)localeArray);
        }
        if (collator == null) {
            for (Locale locale : CompareMode.getCollationLocales(false)) {
                if (!CompareMode.compareLocaleNames(locale, string)) continue;
                collator = Collator.getInstance(locale);
                break;
            }
        }
        return collator;
    }

    public String getName() {
        return this.name == null ? OFF : this.name;
    }

    public int getStrength() {
        return this.strength;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CompareMode)) {
            return false;
        }
        CompareMode compareMode = (CompareMode)object;
        if (!this.getName().equals(compareMode.getName())) {
            return false;
        }
        return this.strength == compareMode.strength;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.getName().hashCode();
        n = 31 * n + this.strength;
        return n;
    }

    @Override
    public int compare(Value value, Value value2) {
        return value.compareTo(value2, null, this);
    }

    static {
        boolean bl = false;
        try {
            Class.forName("com.ibm.icu.text.Collator");
            bl = true;
        }
        catch (Exception exception) {
            // empty catch block
        }
        CAN_USE_ICU4J = bl;
    }
}

