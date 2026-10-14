/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.encrypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FilePathWrapper;
import org.h2.store.fs.FileUtils;
import org.h2.store.fs.encrypt.FileEncrypt;

public class FilePathEncrypt
extends FilePathWrapper {
    private static final String SCHEME = "encrypt";

    public static void register() {
        FilePath.register(new FilePathEncrypt());
    }

    @Override
    public FileChannel open(String string) throws IOException {
        String[] stringArray = this.parse(this.name);
        FileChannel fileChannel = FileUtils.open(stringArray[1], string);
        byte[] byArray = stringArray[0].getBytes(StandardCharsets.UTF_8);
        return new FileEncrypt(this.name, byArray, fileChannel);
    }

    @Override
    public String getScheme() {
        return SCHEME;
    }

    @Override
    protected String getPrefix() {
        String[] stringArray = this.parse(this.name);
        return this.getScheme() + ":" + stringArray[0] + ":";
    }

    @Override
    public FilePath unwrap(String string) {
        return FilePath.get(this.parse(string)[1]);
    }

    @Override
    public long size() {
        long l = this.getBase().size() - 4096L;
        if (((l = Math.max(0L, l)) & 0xFFFL) != 0L) {
            l -= 4096L;
        }
        return l;
    }

    @Override
    public OutputStream newOutputStream(boolean bl) throws IOException {
        return FilePathEncrypt.newFileChannelOutputStream(this.open("rw"), bl);
    }

    @Override
    public InputStream newInputStream() throws IOException {
        return Channels.newInputStream(this.open("r"));
    }

    private String[] parse(String string) {
        if (!string.startsWith(this.getScheme())) {
            throw new IllegalArgumentException(string + " doesn't start with " + this.getScheme());
        }
        int n = (string = string.substring(this.getScheme().length() + 1)).indexOf(58);
        if (n < 0) {
            throw new IllegalArgumentException(string + " doesn't contain encryption algorithm and password");
        }
        String string2 = string.substring(0, n);
        string = string.substring(n + 1);
        return new String[]{string2, string};
    }

    public static byte[] getPasswordBytes(char[] cArray) {
        int n = cArray.length;
        byte[] byArray = new byte[n * 2];
        for (int i = 0; i < n; ++i) {
            char c = cArray[i];
            byArray[i + i] = (byte)(c >>> 8);
            byArray[i + i + 1] = (byte)c;
        }
        return byArray;
    }
}

