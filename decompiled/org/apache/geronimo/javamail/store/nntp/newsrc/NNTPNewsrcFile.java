/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp.newsrc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrc;

public class NNTPNewsrcFile
extends NNTPNewsrc {
    File source;

    public NNTPNewsrcFile(File source2) {
        this.source = source2;
    }

    public BufferedReader getInputReader() throws IOException {
        return new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(this.source), "ISO8859-1"));
    }

    public Writer getOutputWriter() throws IOException {
        return new OutputStreamWriter((OutputStream)new FileOutputStream(this.source, false), "ISO8859-1");
    }
}

