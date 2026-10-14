/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util.sql;

public interface IPAddressSQLTranslator {
    public void setNetwork(String var1);

    public StringBuilder matchString(StringBuilder var1, String var2, String var3);

    public StringBuilder matchSubString(StringBuilder var1, String var2, char var3, int var4, String var5);

    public StringBuilder matchSeparatorCount(StringBuilder var1, String var2, char var3, int var4);

    public StringBuilder boundSeparatorCount(StringBuilder var1, String var2, char var3, int var4);
}

