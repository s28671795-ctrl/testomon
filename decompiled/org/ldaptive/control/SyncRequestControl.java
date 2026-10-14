/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;

public class SyncRequestControl
extends AbstractControl
implements RequestControl {
    public static final String OID = "1.3.6.1.4.1.4203.1.9.1.1";
    private static final int HASH_CODE_SEED = 743;
    private Mode requestMode = Mode.REFRESH_ONLY;
    private byte[] cookie;
    private boolean reloadHint;

    public SyncRequestControl() {
        super(OID);
    }

    public SyncRequestControl(Mode mode) {
        super(OID);
        this.setRequestMode(mode);
    }

    public SyncRequestControl(Mode mode, boolean critical) {
        super(OID, critical);
        this.setRequestMode(mode);
    }

    public SyncRequestControl(Mode mode, byte[] value, boolean critical) {
        super(OID, critical);
        this.setRequestMode(mode);
        this.setCookie(value);
    }

    public SyncRequestControl(Mode mode, byte[] value, boolean hint, boolean critical) {
        super(OID, critical);
        this.setRequestMode(mode);
        this.setCookie(value);
        this.setReloadHint(hint);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public Mode getRequestMode() {
        return this.requestMode;
    }

    public void setRequestMode(Mode mode) {
        this.requestMode = mode;
    }

    public byte[] getCookie() {
        return this.cookie;
    }

    public void setCookie(byte[] value) {
        this.cookie = value;
    }

    public boolean getReloadHint() {
        return this.reloadHint;
    }

    public void setReloadHint(boolean b) {
        this.reloadHint = b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SyncRequestControl && super.equals(o)) {
            SyncRequestControl v = (SyncRequestControl)o;
            return LdapUtils.areEqual((Object)this.requestMode, (Object)v.requestMode) && LdapUtils.areEqual(this.cookie, v.cookie) && LdapUtils.areEqual(this.reloadHint, v.reloadHint);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(743, new Object[]{this.getOID(), this.getCriticality(), this.requestMode, this.cookie, this.reloadHint});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "requestMode=" + (Object)((Object)this.requestMode) + ", " + "cookie=" + LdapUtils.base64Encode(this.cookie) + ", " + "reloadHint=" + this.reloadHint + "]";
    }

    @Override
    public byte[] encode() {
        ConstructedDEREncoder se = this.getCookie() != null ? new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(this.getRequestMode().value()), new OctetStringType(this.getCookie()), new BooleanType(this.getReloadHint())) : new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(this.getRequestMode().value()), new BooleanType(this.getReloadHint()));
        return se.encode();
    }

    public static enum Mode {
        REFRESH_ONLY(1),
        REFRESH_AND_PERSIST(3);

        private final int value;

        private Mode(int i) {
            this.value = i;
        }

        public int value() {
            return this.value;
        }

        public static Mode valueOf(int i) {
            for (Mode m4 : Mode.values()) {
                if (m4.value() != i) continue;
                return m4;
            }
            return null;
        }
    }
}

