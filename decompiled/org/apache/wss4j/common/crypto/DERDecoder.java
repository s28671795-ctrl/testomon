/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.math.BigInteger;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DERDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(DERDecoder.class);
    public static final byte TYPE_BIT_STRING = 3;
    public static final byte TYPE_OCTET_STRING = 4;
    public static final byte TYPE_SEQUENCE = 48;
    private byte[] arr;
    private int pos;

    public DERDecoder(byte[] derEncoded) throws WSSecurityException {
        if (derEncoded == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Invalid DER string"});
        }
        this.arr = derEncoded;
        this.reset();
    }

    public void reset() {
        this.pos = 0;
    }

    public void skip(int length) throws WSSecurityException {
        if (length < 0) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Unsupported DER format"});
        }
        this.pos += length;
    }

    public void expect(int val) throws WSSecurityException {
        this.expect((byte)(val & 0xFF));
    }

    public void expect(byte val) throws WSSecurityException {
        if (!this.test(val)) {
            LOG.debug("DER mismatch: expected " + val + ", got " + this.arr[this.pos]);
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Invalid DER format"});
        }
        ++this.pos;
    }

    public boolean test(byte val) throws WSSecurityException {
        if (this.pos >= this.arr.length) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Invalid DER format"});
        }
        return this.arr[this.pos] == val;
    }

    public int getLength() throws WSSecurityException {
        int len;
        if (this.pos >= this.arr.length) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Invalid DER format"});
        }
        if ((this.arr[this.pos] & 0xFF) <= 127) {
            len = this.arr[this.pos++];
        } else {
            int nbytes;
            if (this.pos + (nbytes = this.arr[this.pos++] & 0x7F) > this.arr.length) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Invalid DER format"});
            }
            byte[] lenBytes = new byte[nbytes];
            System.arraycopy(this.arr, this.pos, lenBytes, 0, lenBytes.length);
            len = new BigInteger(1, lenBytes).intValue();
            this.pos += nbytes;
        }
        return len;
    }

    public byte[] getBytes(int length) throws WSSecurityException {
        if (this.pos + length > this.arr.length) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Invalid DER format"});
        }
        if (length < 0) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN, "noSKIHandling", new Object[]{"Unsupported DER format"});
        }
        byte[] value = new byte[length];
        System.arraycopy(this.arr, this.pos, value, 0, length);
        this.pos += length;
        return value;
    }
}

