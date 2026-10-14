/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import net.lingala.zip4j.io.inputstream.CipherInputStream;
import net.lingala.zip4j.io.inputstream.DecompressedInputStream;

class StoreInputStream
extends DecompressedInputStream {
    public StoreInputStream(CipherInputStream<?> cipherInputStream) {
        super(cipherInputStream);
    }
}

