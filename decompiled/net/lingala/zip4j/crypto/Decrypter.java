/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.crypto;

import net.lingala.zip4j.exception.ZipException;

public interface Decrypter {
    public int decryptData(byte[] var1, int var2, int var3) throws ZipException;
}

