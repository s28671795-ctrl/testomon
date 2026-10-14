/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream_1_0;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.omg.CORBA.CompletionStatus;

public class CDROutputStream_1_1
extends CDROutputStream_1_0 {
    protected int fragmentOffset = 0;

    @Override
    protected void alignAndReserve(int align, int n) {
        int alignment = this.computeAlignment(align);
        if (this.bbwi.position() + n + alignment > this.bbwi.buflen) {
            this.grow(align, n);
            alignment = this.computeAlignment(align);
        }
        this.bbwi.position(this.bbwi.position() + alignment);
    }

    @Override
    protected void grow(int align, int n) {
        int oldSize = this.bbwi.position();
        super.grow(align, n);
        if (this.bbwi.fragmented) {
            this.bbwi.fragmented = false;
            this.fragmentOffset += oldSize - this.bbwi.position();
        }
    }

    @Override
    public int get_offset() {
        return this.bbwi.position() + this.fragmentOffset;
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_1;
    }

    @Override
    public void write_wchar(char x) {
        CodeSetConversion.CTBConverter converter = this.getWCharConverter();
        converter.convert(x);
        if (converter.getNumBytes() != 2) {
            throw this.wrapper.badGiop11Ctb(CompletionStatus.COMPLETED_MAYBE);
        }
        this.alignAndReserve(converter.getAlignment(), converter.getNumBytes());
        this.parent.write_octet_array(converter.getBytes(), 0, converter.getNumBytes());
    }

    @Override
    public void write_wstring(String value) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        int len = value.length() + 1;
        this.write_long(len);
        CodeSetConversion.CTBConverter converter = this.getWCharConverter();
        converter.convert(value);
        this.internalWriteOctetArray(converter.getBytes(), 0, converter.getNumBytes());
        this.write_short((short)0);
    }
}

