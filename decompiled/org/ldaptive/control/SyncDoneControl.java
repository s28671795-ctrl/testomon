/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.ResponseControl;

public class SyncDoneControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "1.3.6.1.4.1.4203.1.9.1.3";
    private static final int HASH_CODE_SEED = 757;
    private byte[] cookie;
    private boolean refreshDeletes;

    public SyncDoneControl() {
        super(OID);
    }

    public SyncDoneControl(boolean critical) {
        super(OID, critical);
    }

    public SyncDoneControl(byte[] value) {
        super(OID);
        this.setCookie(value);
    }

    public SyncDoneControl(byte[] value, boolean critical) {
        super(OID, critical);
        this.setCookie(value);
    }

    public SyncDoneControl(byte[] value, boolean refresh, boolean critical) {
        super(OID, critical);
        this.setCookie(value);
        this.setRefreshDeletes(refresh);
    }

    public byte[] getCookie() {
        return this.cookie;
    }

    public void setCookie(byte[] value) {
        this.cookie = value;
    }

    public boolean getRefreshDeletes() {
        return this.refreshDeletes;
    }

    public void setRefreshDeletes(boolean b) {
        this.refreshDeletes = b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SyncDoneControl && super.equals(o)) {
            SyncDoneControl v = (SyncDoneControl)o;
            return LdapUtils.areEqual(this.cookie, v.cookie) && LdapUtils.areEqual(this.refreshDeletes, v.refreshDeletes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(757, this.getOID(), this.getCriticality(), this.cookie, this.refreshDeletes);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "cookie=" + LdapUtils.base64Encode(this.cookie) + ", " + "refreshDeletes=" + this.refreshDeletes + "]";
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(CookieHandler.PATH, new CookieHandler(this));
        parser.registerHandler(RefreshDeletesHandler.PATH, new RefreshDeletesHandler(this));
        parser.parse(encoded);
    }

    private static class RefreshDeletesHandler
    extends AbstractParseHandler<SyncDoneControl> {
        public static final DERPath PATH = new DERPath("/SEQ/BOOL[1]");

        RefreshDeletesHandler(SyncDoneControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncDoneControl)this.getObject()).setRefreshDeletes(BooleanType.decode(encoded));
        }
    }

    private static class CookieHandler
    extends AbstractParseHandler<SyncDoneControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[0]");

        CookieHandler(SyncDoneControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((SyncDoneControl)this.getObject()).setCookie(cookie);
            }
        }
    }
}

