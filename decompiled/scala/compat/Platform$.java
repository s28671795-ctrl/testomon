/*
 * Decompiled with CFR 0.152.
 */
package scala.compat;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;

public final class Platform$ {
    public static final Platform$ MODULE$ = new Platform$();
    private static final String EOL = System.lineSeparator();

    public void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    public Object createArray(Class<?> elemClass, int length) {
        return Array.newInstance(elemClass, length);
    }

    public void arrayclear(int[] arr) {
        Arrays.fill(arr, 0);
    }

    public Class<?> getClassForName(String name) {
        return Class.forName(name);
    }

    public String EOL() {
        return EOL;
    }

    public long currentTime() {
        return System.currentTimeMillis();
    }

    public void collectGarbage() {
        System.gc();
    }

    public String defaultCharsetName() {
        return Charset.defaultCharset().name();
    }

    private Platform$() {
    }
}

