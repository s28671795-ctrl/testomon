/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbcx;

import java.util.Base64;
import javax.transaction.xa.Xid;
import org.h2.jdbcx.JdbcDataSourceFactory;
import org.h2.message.DbException;
import org.h2.message.TraceObject;

public final class JdbcXid
extends TraceObject
implements Xid {
    private static final String PREFIX = "XID";
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private final int formatId;
    private final byte[] branchQualifier;
    private final byte[] globalTransactionId;

    JdbcXid(JdbcDataSourceFactory jdbcDataSourceFactory, int n, String string) {
        this.setTrace(jdbcDataSourceFactory.getTrace(), 15, n);
        try {
            String[] stringArray = string.split("\\|");
            if (stringArray.length == 4 && PREFIX.equals(stringArray[0])) {
                this.formatId = Integer.parseInt(stringArray[1]);
                Base64.Decoder decoder = Base64.getUrlDecoder();
                this.branchQualifier = decoder.decode(stringArray[2]);
                this.globalTransactionId = decoder.decode(stringArray[3]);
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        throw DbException.get(90101, string);
    }

    static StringBuilder toString(StringBuilder stringBuilder, Xid xid) {
        return stringBuilder.append(PREFIX).append('|').append(xid.getFormatId()).append('|').append(ENCODER.encodeToString(xid.getBranchQualifier())).append('|').append(ENCODER.encodeToString(xid.getGlobalTransactionId()));
    }

    @Override
    public int getFormatId() {
        this.debugCodeCall("getFormatId");
        return this.formatId;
    }

    @Override
    public byte[] getBranchQualifier() {
        this.debugCodeCall("getBranchQualifier");
        return this.branchQualifier;
    }

    @Override
    public byte[] getGlobalTransactionId() {
        this.debugCodeCall("getGlobalTransactionId");
        return this.globalTransactionId;
    }
}

