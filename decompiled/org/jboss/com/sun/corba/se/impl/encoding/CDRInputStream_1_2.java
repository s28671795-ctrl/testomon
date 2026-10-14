/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStreamBase;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream_1_1;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;

public class CDRInputStream_1_2
extends CDRInputStream_1_1 {
    protected boolean headerPadding;
    protected boolean restoreHeaderPadding;

    @Override
    void setHeaderPadding(boolean headerPadding) {
        this.headerPadding = headerPadding;
    }

    @Override
    public void mark(int readlimit) {
        super.mark(readlimit);
        this.restoreHeaderPadding = this.headerPadding;
    }

    @Override
    public void reset() {
        super.reset();
        this.headerPadding = this.restoreHeaderPadding;
        this.restoreHeaderPadding = false;
    }

    @Override
    public CDRInputStreamBase dup() {
        CDRInputStreamBase result = super.dup();
        ((CDRInputStream_1_2)result).headerPadding = this.headerPadding;
        return result;
    }

    @Override
    protected void alignAndCheck(int align, int n) {
        if (this.headerPadding) {
            this.headerPadding = false;
            this.alignOnBoundary(8);
        }
        this.checkBlockLength(align, n);
        int alignIncr = this.computeAlignment(this.bbwi.position(), align);
        this.bbwi.position(this.bbwi.position() + alignIncr);
        if (this.bbwi.position() + n > this.bbwi.buflen) {
            this.grow(1, n);
        }
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_2;
    }

    @Override
    public char read_wchar() {
        byte numBytes = this.read_octet();
        char[] result = this.getConvertedChars(numBytes, this.getWCharConverter());
        if (this.getWCharConverter().getNumChars() > 1) {
            throw this.wrapper.btcResultMoreThanOneChar();
        }
        return result[0];
    }

    @Override
    public String read_wstring() {
        int len = this.read_long();
        if (len == 0) {
            return new String("");
        }
        this.checkForNegativeLength(len);
        return new String(this.getConvertedChars(len, this.getWCharConverter()), 0, this.getWCharConverter().getNumChars());
    }
}

