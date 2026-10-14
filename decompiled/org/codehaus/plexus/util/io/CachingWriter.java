/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Objects;
import org.codehaus.plexus.util.io.CachingOutputStream;

public class CachingWriter
extends OutputStreamWriter {
    private final CachingOutputStream cos;

    public CachingWriter(File path, Charset charset) throws IOException {
        this(Objects.requireNonNull(path).toPath(), charset);
    }

    public CachingWriter(Path path, Charset charset) throws IOException {
        this(path, charset, 32768);
    }

    public CachingWriter(Path path, Charset charset, int bufferSize) throws IOException {
        this(new CachingOutputStream(path, bufferSize), charset);
    }

    private CachingWriter(CachingOutputStream outputStream2, Charset charset) throws IOException {
        super((OutputStream)outputStream2, charset);
        this.cos = outputStream2;
    }

    public boolean isModified() {
        return this.cos.isModified();
    }
}

