/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

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

public class PagedResultsControl
extends AbstractControl
implements RequestControl,
ResponseControl {
    public static final String OID = "1.2.840.113556.1.4.319";
    private static final int HASH_CODE_SEED = 709;
    private static final byte[] EMPTY_COOKIE = new byte[0];
    private int resultSize;
    private byte[] cookie;

    public PagedResultsControl() {
        super(OID);
    }

    public PagedResultsControl(boolean critical) {
        super(OID, critical);
    }

    public PagedResultsControl(int size) {
        super(OID);
        this.setSize(size);
    }

    public PagedResultsControl(int size, boolean critical) {
        super(OID, critical);
        this.setSize(size);
    }

    public PagedResultsControl(int size, byte[] value, boolean critical) {
        super(OID, critical);
        this.setSize(size);
        this.setCookie(value);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public int getSize() {
        return this.resultSize;
    }

    public void setSize(int size) {
        this.resultSize = size;
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
        if (o instanceof PagedResultsControl && super.equals(o)) {
            PagedResultsControl v = (PagedResultsControl)o;
            return LdapUtils.areEqual(this.resultSize, v.resultSize) && LdapUtils.areEqual(this.cookie, v.cookie);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(709, this.getOID(), this.getCriticality(), this.resultSize, this.cookie);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "size=" + this.resultSize + ", " + "cookie=" + LdapUtils.base64Encode(this.cookie) + "]";
    }

    @Override
    public byte[] encode() {
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, new IntegerType(this.getSize()), new OctetStringType(this.getCookie() != null ? this.getCookie() : EMPTY_COOKIE));
        return se.encode();
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(SizeHandler.PATH, new SizeHandler(this));
        parser.registerHandler(CookieHandler.PATH, new CookieHandler(this));
        parser.parse(encoded);
    }

    private static class CookieHandler
    extends AbstractParseHandler<PagedResultsControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[1]");

        CookieHandler(PagedResultsControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((PagedResultsControl)this.getObject()).setCookie(cookie);
            }
        }
    }

    private static class SizeHandler
    extends AbstractParseHandler<PagedResultsControl> {
        public static final DERPath PATH = new DERPath("/SEQ/INT[0]");

        SizeHandler(PagedResultsControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((PagedResultsControl)this.getObject()).setSize(IntegerType.decode(encoded).intValue());
        }
    }
}

