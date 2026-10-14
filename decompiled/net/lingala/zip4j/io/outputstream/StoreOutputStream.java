/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import net.lingala.zip4j.io.outputstream.CipherOutputStream;
import net.lingala.zip4j.io.outputstream.CompressedOutputStream;

class StoreOutputStream
extends CompressedOutputStream {
    public StoreOutputStream(CipherOutputStream<?> cipherOutputStream) {
        super(cipherOutputStream);
    }
}

