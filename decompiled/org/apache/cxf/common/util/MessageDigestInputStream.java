/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.cxf.common.util.Base64UrlUtility;
import org.apache.cxf.common.util.Base64Utility;

public class MessageDigestInputStream
extends DigestInputStream {
    public static final String ALGO_SHA_256 = "SHA-256";

    public MessageDigestInputStream(InputStream is) {
        super(is, MessageDigestInputStream.getDigestInstance(ALGO_SHA_256));
    }

    private static MessageDigest getDigestInstance(String algo) {
        try {
            return MessageDigest.getInstance(algo);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new SecurityException(ex);
        }
    }

    public byte[] getDigestBytes() {
        return super.getMessageDigest().digest();
    }

    public String getBase64Digest() {
        return Base64Utility.encode(this.getDigestBytes());
    }

    public String getBase64UrlDigest() {
        return Base64UrlUtility.encode(this.getDigestBytes());
    }
}

