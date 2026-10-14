/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import java.io.IOException;
import java.io.OutputStream;

public class NonBlockingJsonParser
extends NonBlockingUtf8JsonParserBase
implements ByteArrayFeeder {
    private byte[] _inputBuffer = NO_BYTES;

    public NonBlockingJsonParser(IOContext ctxt, int parserFeatures, ByteQuadsCanonicalizer sym) {
        super(ctxt, parserFeatures, sym);
    }

    @Override
    public ByteArrayFeeder getNonBlockingInputFeeder() {
        return this;
    }

    @Override
    public void feedInput(byte[] buf, int start2, int end) throws IOException {
        if (this._inputPtr < this._inputEnd) {
            this._reportError("Still have %d undecoded bytes, should not call 'feedInput'", this._inputEnd - this._inputPtr);
        }
        if (end < start2) {
            this._reportError("Input end (%d) may not be before start (%d)", end, start2);
        }
        if (this._endOfInput) {
            this._reportError("Already closed, can not feed more input");
        }
        this._currInputProcessed += (long)this._origBufferLen;
        this._currInputRowStart = start2 - (this._inputEnd - this._currInputRowStart);
        this._currBufferStart = start2;
        this._inputBuffer = buf;
        this._inputPtr = start2;
        this._inputEnd = end;
        this._origBufferLen = end - start2;
    }

    @Override
    public int releaseBuffered(OutputStream out) throws IOException {
        int avail = this._inputEnd - this._inputPtr;
        if (avail > 0) {
            out.write(this._inputBuffer, this._inputPtr, avail);
        }
        return avail;
    }

    @Override
    protected byte getNextSignedByteFromBuffer() {
        return this._inputBuffer[this._inputPtr++];
    }

    @Override
    protected int getNextUnsignedByteFromBuffer() {
        return this._inputBuffer[this._inputPtr++] & 0xFF;
    }

    @Override
    protected byte getByteFromBuffer(int ptr) {
        return this._inputBuffer[ptr];
    }
}

