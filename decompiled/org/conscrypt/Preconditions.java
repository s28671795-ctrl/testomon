/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

final class Preconditions {
    private Preconditions() {
    }

    static <T> T checkNotNull(T reference, String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(errorMessage);
        }
        return reference;
    }

    static void checkArgument(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    static void checkArgument(boolean condition, String errorMessageTemplate, Object arg) {
        if (!condition) {
            throw new IllegalArgumentException(String.format(errorMessageTemplate, arg));
        }
    }

    static void checkPositionIndexes(int start2, int end, int size) {
        if (start2 < 0 || end < start2 || end > size) {
            throw new IndexOutOfBoundsException(Preconditions.badPositionIndexes(start2, end, size));
        }
    }

    private static String badPositionIndexes(int start2, int end, int size) {
        if (start2 < 0 || start2 > size) {
            return Preconditions.badPositionIndex(start2, size, "start index");
        }
        if (end < 0 || end > size) {
            return Preconditions.badPositionIndex(end, size, "end index");
        }
        return String.format("end index (%s) must not be less than start index (%s)", end, start2);
    }

    private static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return String.format("%s (%s) must not be negative", desc, index);
        }
        if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        }
        return String.format("%s (%s) must not be greater than size (%s)", desc, index, size);
    }
}

