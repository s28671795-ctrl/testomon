/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.UniversalDERTag;

public class OidType
extends AbstractDERType
implements DEREncoder {
    private final byte[] derItem;

    public OidType(String item) {
        super(UniversalDERTag.OID);
        this.derItem = OidType.toBytes(OidType.parse(item));
    }

    public OidType(int[] item) {
        super(UniversalDERTag.OID);
        this.derItem = OidType.toBytes(item);
    }

    public OidType(DERTag tag, String item) {
        super(tag);
        if (tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must not be constructed");
        }
        this.derItem = OidType.toBytes(OidType.parse(item));
    }

    public OidType(DERTag tag, int[] item) {
        super(tag);
        if (tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must not be constructed");
        }
        this.derItem = OidType.toBytes(item);
    }

    @Override
    public byte[] encode() {
        return this.encode(new byte[][]{this.derItem});
    }

    public static String decode(DERBuffer encoded) {
        StringBuilder sb = new StringBuilder();
        byte firstId = encoded.get();
        if (firstId < 40) {
            sb.append("0").append(".").append(firstId).append(".");
        } else if (firstId < 80) {
            sb.append("1").append(".").append(firstId - 40).append(".");
        } else {
            sb.append("2").append(".").append(firstId - 80).append(".");
        }
        while (encoded.hasRemaining()) {
            sb.append(OidType.readInt(encoded)).append(".");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static byte[] toBytes(int[] oid) {
        OidType.isValid(oid);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            try {
                if (oid[0] < 2) {
                    bytes.write(oid[0] * 40 + oid[1]);
                } else {
                    bytes.write(OidType.toBytes(oid[0] * 40 + oid[1]));
                }
                for (int i = 2; i < oid.length; ++i) {
                    bytes.write(OidType.toBytes(oid[i]));
                }
            }
            finally {
                bytes.close();
            }
        }
        catch (IOException e) {
            throw new IllegalStateException("Byte conversion failed", e);
        }
        return bytes.toByteArray();
    }

    protected static void isValid(int[] oid) {
        if (oid == null || oid.length < 2) {
            throw new IllegalArgumentException("OIDs must have at least two components");
        }
        if (oid[0] < 0 || oid[0] > 2) {
            throw new IllegalArgumentException("The first OID must be 0, 1, or 2");
        }
        if (oid[0] < 2 && oid[1] > 39) {
            throw new IllegalArgumentException("The second OID must be less than or equal to 38");
        }
        for (int i : oid) {
            if (i >= 0) continue;
            throw new IllegalArgumentException("OIDs cannot be negative");
        }
    }

    protected static byte[] toBytes(int component) {
        byte[] buffer = new byte[4];
        int size = 0;
        for (int val = component; val != 0; val >>>= 7) {
            buffer[size++] = size > 0 ? (byte)(val & 0x7F | 0x80) : (byte)(val & 0x7F);
        }
        byte[] bytes = new byte[size];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = buffer[--size];
        }
        return bytes;
    }

    protected static int readInt(DERBuffer buffer) {
        int val = 0;
        for (int i = 0; i < 4; ++i) {
            byte b = buffer.get();
            if (i == 0 && b == 128) {
                throw new IllegalArgumentException("Component starts with 0x80");
            }
            val <<= 7;
            val |= b & 0x7F;
            if ((b & 0x80) != 0) continue;
            return val;
        }
        throw new IllegalArgumentException("Integer greater than 4 bytes in size");
    }

    public static int[] parse(String oid) {
        if (oid == null) {
            throw new IllegalArgumentException("OID cannot be null");
        }
        StringTokenizer st = new StringTokenizer(oid, ".");
        int[] oids = new int[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            try {
                oids[i++] = Integer.parseInt(st.nextToken());
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        }
        OidType.isValid(oids);
        return oids;
    }
}

