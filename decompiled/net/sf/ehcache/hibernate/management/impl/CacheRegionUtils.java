/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.impl;

import java.awt.Color;

public abstract class CacheRegionUtils {
    public static final Color HIT_COLOR = Color.green;
    public static final Color MISS_COLOR = Color.red;
    public static final Color PUT_COLOR = Color.blue;
    public static final Color HIT_FILL_COLOR = HIT_COLOR.brighter().brighter().brighter();
    public static final Color MISS_FILL_COLOR = MISS_COLOR.brighter().brighter().brighter();
    public static final Color PUT_FILL_COLOR = PUT_COLOR.brighter().brighter().brighter();
    public static final Color HIT_DRAW_COLOR = HIT_COLOR.darker();
    public static final Color MISS_DRAW_COLOR = MISS_COLOR.darker();
    public static final Color PUT_DRAW_COLOR = PUT_COLOR.darker();

    public static String determineShortName(String fullName) {
        String result = fullName;
        if (fullName != null) {
            String[] comps = fullName.split("\\.");
            if (comps.length == 1) {
                return fullName;
            }
            boolean truncate = true;
            for (int i = 0; i < comps.length; ++i) {
                String comp = comps[i];
                char c = comp.charAt(0);
                if (truncate && Character.isUpperCase(c)) {
                    truncate = false;
                }
                if (!truncate) continue;
                comps[i] = Character.toString(c);
            }
            result = CacheRegionUtils.join(comps, '.');
        }
        return result;
    }

    private static String join(String[] elements, char c) {
        if (elements == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s2 : elements) {
            sb.append(s2).append(c);
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }
}

