/*
 * Decompiled with CFR 0.152.
 */
package liquibase.plugin;

public interface Plugin {
    public static final int PRIORITY_NOT_APPLICABLE = -1;
    public static final int PRIORITY_DEFAULT = 1;
    public static final int PRIORITY_SPECIALIZED = 10;
    public static final int PRIORITY_DATABASE = 5;
}

