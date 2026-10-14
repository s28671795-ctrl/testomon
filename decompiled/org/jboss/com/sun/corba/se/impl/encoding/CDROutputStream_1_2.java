/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.encoding;

import org.jboss.com.sun.corba.se.impl.encoding.CDROutputStream_1_1;
import org.jboss.com.sun.corba.se.impl.encoding.CodeSetConversion;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.omg.CORBA.CompletionStatus;

public class CDROutputStream_1_2
extends CDROutputStream_1_1 {
    protected boolean primitiveAcrossFragmentedChunk = false;
    protected boolean specialChunk = false;
    private boolean headerPadding;

    @Override
    protected void handleSpecialChunkBegin(int requiredSize) {
        if (this.inBlock && requiredSize + this.bbwi.position() > this.bbwi.buflen) {
            int oldSize = this.bbwi.position();
            this.bbwi.position(this.blockSizeIndex - 4);
            this.writeLongWithoutAlign(oldSize - this.blockSizeIndex + requiredSize);
            this.bbwi.position(oldSize);
            this.specialChunk = true;
        }
    }

    @Override
    protected void handleSpecialChunkEnd() {
        if (this.inBlock && this.specialChunk) {
            this.inBlock = false;
            this.blockSizeIndex = -1;
            this.blockSizePosition = -1;
            this.start_block();
            this.specialChunk = false;
        }
    }

    private void checkPrimitiveAcrossFragmentedChunk() {
        if (this.primitiveAcrossFragmentedChunk) {
            this.primitiveAcrossFragmentedChunk = false;
            this.inBlock = false;
            this.blockSizeIndex = -1;
            this.blockSizePosition = -1;
            this.start_block();
        }
    }

    @Override
    public void write_octet(byte x) {
        super.write_octet(x);
        this.checkPrimitiveAcrossFragmentedChunk();
    }

    @Override
    public void write_short(short x) {
        super.write_short(x);
        this.checkPrimitiveAcrossFragmentedChunk();
    }

    @Override
    public void write_long(int x) {
        super.write_long(x);
        this.checkPrimitiveAcrossFragmentedChunk();
    }

    @Override
    public void write_longlong(long x) {
        super.write_longlong(x);
        this.checkPrimitiveAcrossFragmentedChunk();
    }

    @Override
    void setHeaderPadding(boolean headerPadding) {
        this.headerPadding = headerPadding;
    }

    @Override
    protected void alignAndReserve(int align, int n) {
        if (this.headerPadding) {
            this.headerPadding = false;
            this.alignOnBoundary(8);
        }
        this.bbwi.position(this.bbwi.position() + this.computeAlignment(align));
        if (this.bbwi.position() + n > this.bbwi.buflen) {
            this.grow(align, n);
        }
    }

    @Override
    protected void grow(int align, int n) {
        boolean handleChunk;
        int oldSize = this.bbwi.position();
        boolean bl = handleChunk = this.inBlock && !this.specialChunk;
        if (handleChunk) {
            int oldIndex = this.bbwi.position();
            this.bbwi.position(this.blockSizeIndex - 4);
            this.writeLongWithoutAlign(oldIndex - this.blockSizeIndex + n);
            this.bbwi.position(oldIndex);
        }
        this.bbwi.needed = n;
        this.bufferManagerWrite.overflow(this.bbwi);
        if (this.bbwi.fragmented) {
            this.bbwi.fragmented = false;
            this.fragmentOffset += oldSize - this.bbwi.position();
            if (handleChunk) {
                this.primitiveAcrossFragmentedChunk = true;
            }
        }
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return GIOPVersion.V1_2;
    }

    @Override
    public void write_wchar(char x) {
        CodeSetConversion.CTBConverter converter = this.getWCharConverter();
        converter.convert(x);
        this.handleSpecialChunkBegin(1 + converter.getNumBytes());
        this.write_octet((byte)converter.getNumBytes());
        byte[] result = converter.getBytes();
        this.internalWriteOctetArray(result, 0, converter.getNumBytes());
        this.handleSpecialChunkEnd();
    }

    @Override
    public void write_wchar_array(char[] value, int offset, int length) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        CodeSetConversion.CTBConverter converter = this.getWCharConverter();
        int totalNumBytes = 0;
        int maxLength = (int)Math.ceil(converter.getMaxBytesPerChar() * (float)length);
        byte[] buffer = new byte[maxLength + length];
        for (int i = 0; i < length; ++i) {
            converter.convert(value[offset + i]);
            buffer[totalNumBytes++] = (byte)converter.getNumBytes();
            System.arraycopy(converter.getBytes(), 0, buffer, totalNumBytes, converter.getNumBytes());
            totalNumBytes += converter.getNumBytes();
        }
        this.handleSpecialChunkBegin(totalNumBytes);
        this.internalWriteOctetArray(buffer, 0, totalNumBytes);
        this.handleSpecialChunkEnd();
    }

    @Override
    public void write_wstring(String value) {
        if (value == null) {
            throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
        }
        if (value.length() == 0) {
            this.write_long(0);
            return;
        }
        CodeSetConversion.CTBConverter converter = this.getWCharConverter();
        converter.convert(value);
        this.handleSpecialChunkBegin(this.computeAlignment(4) + 4 + converter.getNumBytes());
        this.write_long(converter.getNumBytes());
        this.internalWriteOctetArray(converter.getBytes(), 0, converter.getNumBytes());
        this.handleSpecialChunkEnd();
    }
}

