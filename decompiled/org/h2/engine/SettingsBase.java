/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.h2.message.DbException;
import org.h2.util.Utils;

public class SettingsBase {
    private final HashMap<String, String> settings;

    protected SettingsBase(HashMap<String, String> hashMap) {
        this.settings = hashMap;
    }

    protected boolean get(String string, boolean bl) {
        String string2 = this.get(string, Boolean.toString(bl));
        try {
            return Utils.parseBoolean(string2, bl, true);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw DbException.get(22018, illegalArgumentException, "key:" + string + " value:" + string2);
        }
    }

    void set(String string, boolean bl) {
        this.settings.put(string, Boolean.toString(bl));
    }

    protected int get(String string, int n) {
        String string2 = this.get(string, Integer.toString(n));
        try {
            return Integer.decode(string2);
        }
        catch (NumberFormatException numberFormatException) {
            throw DbException.get(22018, numberFormatException, "key:" + string + " value:" + string2);
        }
    }

    protected String get(String string, String string2) {
        String string3 = this.settings.get(string);
        if (string3 != null) {
            return string3;
        }
        StringBuilder stringBuilder = new StringBuilder("h2.");
        boolean bl = false;
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '_') {
                bl = true;
                continue;
            }
            stringBuilder.append(bl ? Character.toUpperCase(c) : Character.toLowerCase(c));
            bl = false;
        }
        String string4 = stringBuilder.toString();
        string3 = Utils.getProperty(string4, string2);
        this.settings.put(string, string3);
        return string3;
    }

    protected boolean containsKey(String string) {
        return this.settings.containsKey(string);
    }

    public HashMap<String, String> getSettings() {
        return this.settings;
    }

    public Map.Entry<String, String>[] getSortedSettings() {
        Map.Entry[] entryArray = this.settings.entrySet().toArray(new Map.Entry[0]);
        Arrays.sort(entryArray, Comparator.comparing(Map.Entry::getKey));
        return entryArray;
    }
}

