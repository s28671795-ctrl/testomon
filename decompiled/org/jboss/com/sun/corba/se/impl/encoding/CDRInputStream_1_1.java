/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStreamBase;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream_1_0;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;

public class CDRInputStream_1_1
extends CDRInputStream_1_0 {
    protected int fragmentOffset = 0;

    @Override
    public GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_1;
    }

    @Override
    public CDRInputStreamBase dup() {
        CDRInputStreamBase result = super.dup();
        ((CDRInputStream_1_1)result).fragmentOffset = this.fragmentOffset;
        return result;
    }

    @Override
    protected int get_offset() {
        return this.bbwi.position() + this.fragmentOffset;
    }

    @Override
    protected void alignAndCheck(int align, int n) {
        this.checkBlockLength(align, n);
        int alignment = this.computeAlignment(this.bbwi.position(), align);
        if (this.bbwi.position() + n + alignment > this.bbwi.buflen) {
            if (this.bbwi.position() + alignment == this.bbwi.buflen) {
                this.bbwi.position(this.bbwi.position() + alignment);
            }
            this.grow(align, n);
            alignment = this.computeAlignment(this.bbwi.position(), align);
        }
        this.bbwi.position(this.bbwi.position() + alignment);
    }

    @Override
    protected void grow(int align, int n) {
        this.bbwi.needed = n;
        int oldSize = this.bbwi.position();
        this.bbwi = this.bufferManagerRead.underflow(this.bbwi);
        if (this.bbwi.fragmented) {
            this.fragmentOffset += oldSize - this.bbwi.position();
            this.markAndResetHandler.fragmentationOccured(this.bbwi);
            this.bbwi.fragmented = false;
        }
    }

    @Override
    public Object createStreamMemento() {
        return new FragmentableStreamMemento();
    }

    @Override
    public void restoreInternalState(Object streamMemento) {
        super.restoreInternalState(streamMemento);
        this.fragmentOffset = ((FragmentableStreamMemento)streamMemento).fragmentOffset_;
    }

    @Override
    public char read_wchar() {
        this.alignAndCheck(2, 2);
        char[] result = this.getConvertedChars(2, this.getWCharConverter());
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
        char[] result = this.getConvertedChars(--len * 2, this.getWCharConverter());
        this.read_short();
        return new String(result, 0, this.getWCharConverter().getNumChars());
    }

    private class FragmentableStreamMemento
    extends CDRInputStream_1_0.StreamMemento {
        private int fragmentOffset_;

        public FragmentableStreamMemento() {
            this.fragmentOffset_ = CDRInputStream_1_1.this.fragmentOffset;
        }
    }
}

