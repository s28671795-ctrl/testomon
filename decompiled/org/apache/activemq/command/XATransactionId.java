/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.transaction.xa.Xid;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.util.DataByteArrayInputStream;
import org.apache.activemq.util.DataByteArrayOutputStream;
import org.apache.activemq.util.JenkinsHash;

public class XATransactionId
extends TransactionId
implements Xid,
Comparable {
    public static final byte DATA_STRUCTURE_TYPE = 112;
    private int formatId;
    private byte[] branchQualifier;
    private byte[] globalTransactionId;
    private transient DataByteArrayOutputStream outputStream;
    private transient byte[] encodedXidBytes;
    private transient int hash;
    private transient String transactionKey;
    private transient ArrayList<MessageAck> preparedAcks;
    final int XID_PREFIX_SIZE = 16;

    public XATransactionId() {
    }

    public XATransactionId(Xid xid) {
        this.formatId = xid.getFormatId();
        this.globalTransactionId = xid.getGlobalTransactionId();
        this.branchQualifier = xid.getBranchQualifier();
    }

    public XATransactionId(byte[] encodedBytes) throws IOException {
        this.encodedXidBytes = encodedBytes;
        this.initFromEncodedBytes();
    }

    @Override
    public byte getDataStructureType() {
        return 112;
    }

    private void initFromEncodedBytes() throws IOException {
        DataByteArrayInputStream inputStream2 = new DataByteArrayInputStream(this.encodedXidBytes);
        inputStream2.skipBytes(10);
        this.formatId = inputStream2.readInt();
        short globalLength = inputStream2.readShort();
        this.globalTransactionId = new byte[globalLength];
        try {
            inputStream2.read(this.globalTransactionId);
            this.branchQualifier = new byte[inputStream2.available()];
            inputStream2.read(this.branchQualifier);
        }
        catch (IOException fatal) {
            throw new RuntimeException(this + ", failed to decode:", fatal);
        }
    }

    public synchronized byte[] getEncodedXidBytes() {
        if (this.encodedXidBytes == null) {
            this.outputStream = new DataByteArrayOutputStream(16 + this.globalTransactionId.length + this.branchQualifier.length);
            this.outputStream.position(10);
            this.outputStream.writeInt(this.formatId);
            this.outputStream.writeShort(this.globalTransactionId.length);
            try {
                this.outputStream.write(this.globalTransactionId);
                this.outputStream.write(this.branchQualifier);
            }
            catch (IOException fatal) {
                throw new RuntimeException(this + ", failed to encode:", fatal);
            }
            this.encodedXidBytes = this.outputStream.getData();
        }
        return this.encodedXidBytes;
    }

    public DataByteArrayOutputStream internalOutputStream() {
        return this.outputStream;
    }

    @Override
    public synchronized String getTransactionKey() {
        if (this.transactionKey == null) {
            StringBuffer s2 = new StringBuffer();
            s2.append("XID:[" + this.formatId + ",globalId=");
            s2.append(this.stringForm(this.formatId, this.globalTransactionId));
            s2.append(",branchId=");
            s2.append(this.stringForm(this.formatId, this.branchQualifier));
            s2.append("]");
            this.transactionKey = s2.toString();
        }
        return this.transactionKey;
    }

    private String stringForm(int format, byte[] uid) {
        StringBuffer s2 = new StringBuffer();
        switch (format) {
            case 131077: {
                this.stringFormArj(s2, uid);
                break;
            }
            default: {
                this.stringFormDefault(s2, uid);
            }
        }
        return s2.toString();
    }

    private void stringFormDefault(StringBuffer s2, byte[] uid) {
        for (int i = 0; i < uid.length; ++i) {
            s2.append(Integer.toHexString(uid[i]));
        }
    }

    private void stringFormArj(StringBuffer s2, byte[] uid) {
        try {
            DataByteArrayInputStream byteArrayInputStream = new DataByteArrayInputStream(uid);
            s2.append(Long.toString(byteArrayInputStream.readLong(), 16));
            s2.append(':');
            s2.append(Long.toString(byteArrayInputStream.readLong(), 16));
            s2.append(':');
            s2.append(Integer.toString(byteArrayInputStream.readInt(), 16));
            s2.append(':');
            s2.append(Integer.toString(byteArrayInputStream.readInt(), 16));
            s2.append(':');
            s2.append(Integer.toString(byteArrayInputStream.readInt(), 16));
        }
        catch (Exception ignored) {
            this.stringFormDefault(s2, uid);
        }
    }

    public String toString() {
        return this.getTransactionKey();
    }

    @Override
    public boolean isXATransaction() {
        return true;
    }

    @Override
    public boolean isLocalTransaction() {
        return false;
    }

    @Override
    public int getFormatId() {
        return this.formatId;
    }

    @Override
    public byte[] getGlobalTransactionId() {
        return this.globalTransactionId;
    }

    @Override
    public byte[] getBranchQualifier() {
        return this.branchQualifier;
    }

    public void setBranchQualifier(byte[] branchQualifier) {
        this.branchQualifier = branchQualifier;
        this.hash = 0;
    }

    public void setFormatId(int formatId) {
        this.formatId = formatId;
        this.hash = 0;
    }

    public void setGlobalTransactionId(byte[] globalTransactionId) {
        this.globalTransactionId = globalTransactionId;
        this.hash = 0;
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash = this.formatId;
            JenkinsHash jh = JenkinsHash.getInstance();
            this.hash = jh.hash(this.globalTransactionId, this.hash);
            this.hash = jh.hash(this.branchQualifier, this.hash);
            if (this.hash == 0) {
                this.hash = 0xACEACE;
            }
        }
        return this.hash;
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != XATransactionId.class) {
            return false;
        }
        XATransactionId xid = (XATransactionId)o;
        return xid.formatId == this.formatId && Arrays.equals(xid.globalTransactionId, this.globalTransactionId) && Arrays.equals(xid.branchQualifier, this.branchQualifier);
    }

    public int compareTo(Object o) {
        if (o == null || o.getClass() != XATransactionId.class) {
            return -1;
        }
        XATransactionId xid = (XATransactionId)o;
        return this.getTransactionKey().compareTo(xid.getTransactionKey());
    }

    public void setPreparedAcks(ArrayList<MessageAck> preparedAcks) {
        this.preparedAcks = preparedAcks;
    }

    public ArrayList<MessageAck> getPreparedAcks() {
        return this.preparedAcks;
    }
}

