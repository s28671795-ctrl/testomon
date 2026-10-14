/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.contrib.org.apache.commons.codec_1_3;

import org.jasypt.contrib.org.apache.commons.codec_1_3.Encoder;
import org.jasypt.contrib.org.apache.commons.codec_1_3.EncoderException;

public interface BinaryEncoder
extends Encoder {
    public byte[] encode(byte[] var1) throws EncoderException;
}

