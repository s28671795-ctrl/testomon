/*
 * Decompiled with CFR 0.152.
 */
package liquibase.dbdoc;

public class DBDocUtil {
    public static String toFileName(String string) {
        return string.replaceAll("[^\\w\\.\\\\/-]", "_");
    }
}

