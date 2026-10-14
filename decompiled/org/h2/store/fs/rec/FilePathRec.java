/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.rec;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import org.h2.store.fs.FilePath;
import org.h2.store.fs.FilePathWrapper;
import org.h2.store.fs.Recorder;
import org.h2.store.fs.rec.FileRec;

public class FilePathRec
extends FilePathWrapper {
    private static final FilePathRec INSTANCE = new FilePathRec();
    private static Recorder recorder;
    private boolean trace;

    public static void register() {
        FilePath.register(INSTANCE);
    }

    public static void setRecorder(Recorder recorder) {
        FilePathRec.recorder = recorder;
    }

    @Override
    public boolean createFile() {
        this.log(2, this.name);
        return super.createFile();
    }

    @Override
    public FilePath createTempFile(String string, boolean bl) throws IOException {
        this.log(3, this.unwrap(this.name) + ":" + string + ":" + bl);
        return super.createTempFile(string, bl);
    }

    @Override
    public void delete() {
        this.log(4, this.name);
        super.delete();
    }

    @Override
    public FileChannel open(String string) throws IOException {
        return new FileRec(this, super.open(string), this.name);
    }

    @Override
    public OutputStream newOutputStream(boolean bl) throws IOException {
        this.log(5, this.name);
        return super.newOutputStream(bl);
    }

    @Override
    public void moveTo(FilePath filePath, boolean bl) {
        this.log(6, this.unwrap(this.name) + ":" + this.unwrap(filePath.name));
        super.moveTo(filePath, bl);
    }

    public boolean isTrace() {
        return this.trace;
    }

    public void setTrace(boolean bl) {
        this.trace = bl;
    }

    void log(int n, String string) {
        this.log(n, string, null, 0L);
    }

    void log(int n, String string, byte[] byArray, long l) {
        if (recorder != null) {
            recorder.log(n, string, byArray, l);
        }
    }

    @Override
    public String getScheme() {
        return "rec";
    }
}

