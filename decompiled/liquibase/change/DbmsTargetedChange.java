/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

public interface DbmsTargetedChange {
    public String getDbms();

    public void setDbms(String var1);
}

