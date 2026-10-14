/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

public class NumberUtils {
    public static int requirePositive(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("number must be greater than 0");
        }
        return number;
    }

    public static double requirePositive(double number) {
        if (number <= 0.0) {
            throw new IllegalArgumentException("number must be greater than 0");
        }
        return number;
    }
}

