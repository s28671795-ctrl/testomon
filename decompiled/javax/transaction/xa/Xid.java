/*
 * Decompiled with CFR 0.152.
 */
package javax.transaction.xa;

public interface Xid {
    public static final int MAXGTRIDSIZE = 64;
    public static final int MAXBQUALSIZE = 64;

    public byte[] getBranchQualifier();

    public int getFormatId();

    public byte[] getGlobalTransactionId();
}

