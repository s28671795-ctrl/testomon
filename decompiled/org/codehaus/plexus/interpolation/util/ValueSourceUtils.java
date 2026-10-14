/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.util;

import java.util.Collection;

public final class ValueSourceUtils {
    private ValueSourceUtils() {
    }

    public static String trimPrefix(String expression, Collection<String> possiblePrefixes, boolean allowUnprefixedExpressions) {
        if (expression == null) {
            return null;
        }
        String realExpr = null;
        for (String prefix : possiblePrefixes) {
            if (!expression.startsWith(prefix)) continue;
            realExpr = expression.substring(prefix.length());
            if (!realExpr.startsWith(".")) break;
            realExpr = realExpr.substring(1);
            break;
        }
        if (realExpr == null && allowUnprefixedExpressions) {
            realExpr = expression;
        }
        return realExpr;
    }

    public static String trimPrefix(String expression, String[] possiblePrefixes, boolean allowUnprefixedExpressions) {
        if (expression == null) {
            return null;
        }
        String realExpr = null;
        for (String prefix : possiblePrefixes) {
            if (!expression.startsWith(prefix)) continue;
            realExpr = expression.substring(prefix.length());
            if (!realExpr.startsWith(".")) break;
            realExpr = realExpr.substring(1);
            break;
        }
        if (realExpr == null && allowUnprefixedExpressions) {
            realExpr = expression;
        }
        return realExpr;
    }
}

