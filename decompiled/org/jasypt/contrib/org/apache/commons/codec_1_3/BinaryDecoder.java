/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.contrib.org.apache.commons.codec_1_3;

import org.jasypt.contrib.org.apache.commons.codec_1_3.Decoder;
import org.jasypt.contrib.org.apache.commons.codec_1_3.DecoderException;

public interface BinaryDecoder
extends Decoder {
    public byte[] decode(byte[] var1) throws DecoderException;
}

