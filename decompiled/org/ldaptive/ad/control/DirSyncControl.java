/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.control;

import java.math.BigInteger;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.control.ResponseControl;

public class DirSyncControl
extends AbstractControl
implements RequestControl,
ResponseControl {
    public static final String OID = "1.2.840.113556.1.4.841";
    private static final int HASH_CODE_SEED = 907;
    private static final byte[] EMPTY_COOKIE = new byte[0];
    private long flags;
    private int maxAttributeCount;
    private byte[] cookie;

    public DirSyncControl() {
        super(OID);
    }

    public DirSyncControl(boolean critical) {
        this(null, null, critical);
    }

    public DirSyncControl(Flag[] f) {
        this(f, false);
    }

    public DirSyncControl(Flag[] f, boolean critical) {
        this(f, null, critical);
    }

    public DirSyncControl(Flag[] f, int count) {
        this(f, null, count, false);
    }

    public DirSyncControl(Flag[] f, int count, boolean critical) {
        this(f, null, count, critical);
    }

    public DirSyncControl(Flag[] f, byte[] value, boolean critical) {
        this(f, value, 0, critical);
    }

    public DirSyncControl(Flag[] f, byte[] value, int count, boolean critical) {
        super(OID, critical);
        if (f != null) {
            long l = 0L;
            for (Flag flag : f) {
                if (flag == null) continue;
                l += flag.value();
            }
            this.setFlags(l);
        }
        this.setCookie(value);
        this.setMaxAttributeCount(count);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public long getFlags() {
        return this.flags;
    }

    public void setFlags(long l) {
        this.flags = l;
    }

    public int getMaxAttributeCount() {
        return this.maxAttributeCount;
    }

    public void setMaxAttributeCount(int count) {
        this.maxAttributeCount = count;
    }

    public byte[] getCookie() {
        return this.cookie;
    }

    public void setCookie(byte[] value) {
        this.cookie = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DirSyncControl && super.equals(o)) {
            DirSyncControl v = (DirSyncControl)o;
            return LdapUtils.areEqual(this.flags, v.flags) && LdapUtils.areEqual(this.maxAttributeCount, v.maxAttributeCount) && LdapUtils.areEqual(this.cookie, v.cookie);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(907, this.getOID(), this.getCriticality(), this.flags, this.maxAttributeCount, this.cookie);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "flags=" + this.flags + ", " + "maxAttributeCount=" + this.maxAttributeCount + ", " + "cookie=" + LdapUtils.base64Encode(this.cookie) + "]";
    }

    @Override
    public byte[] encode() {
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(BigInteger.valueOf(this.getFlags())), new IntegerType(this.getMaxAttributeCount()), new OctetStringType(this.getCookie() != null ? this.getCookie() : EMPTY_COOKIE));
        return se.encode();
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(FlagHandler.PATH, new FlagHandler(this));
        parser.registerHandler(MaxAttrCountHandler.PATH, new MaxAttrCountHandler(this));
        parser.registerHandler(CookieHandler.PATH, new CookieHandler(this));
        parser.parse(encoded);
    }

    private static class CookieHandler
    extends AbstractParseHandler<DirSyncControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[2]");

        CookieHandler(DirSyncControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((DirSyncControl)this.getObject()).setCookie(cookie);
            }
        }
    }

    private static class MaxAttrCountHandler
    extends AbstractParseHandler<DirSyncControl> {
        public static final DERPath PATH = new DERPath("/SEQ/INT[1]");

        MaxAttrCountHandler(DirSyncControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((DirSyncControl)this.getObject()).setMaxAttributeCount(IntegerType.decode(encoded).intValue());
        }
    }

    private static class FlagHandler
    extends AbstractParseHandler<DirSyncControl> {
        public static final DERPath PATH = new DERPath("/SEQ/INT[0]");

        FlagHandler(DirSyncControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((DirSyncControl)this.getObject()).setFlags(IntegerType.decode(encoded).longValue());
        }
    }

    public static enum Flag {
        OBJECT_SECURITY(1L),
        ANCESTORS_FIRST_ORDER(2048L),
        PUBLIC_DATA_ONLY(8192L),
        INCREMENTAL_VALUES(0x80000000L);

        private final long value;

        private Flag(long l) {
            this.value = l;
        }

        public long value() {
            return this.value;
        }

        public static Flag valueOf(long l) {
            for (Flag f : Flag.values()) {
                if (f.value() != l) continue;
                return f;
            }
            return null;
        }
    }
}

