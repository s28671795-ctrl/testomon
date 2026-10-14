/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.ICookable;
import org.codehaus.commons.nullanalysis.Nullable;

public abstract class Cookable
implements ICookable {
    @Override
    public abstract void cook(@Nullable String var1, Reader var2) throws CompileException, IOException;

    @Override
    public final void cook(Reader r) throws CompileException, IOException {
        this.cook(null, r);
    }

    @Override
    public final void cook(InputStream is) throws CompileException, IOException {
        this.cook(null, is);
    }

    @Override
    public final void cook(@Nullable String fileName, InputStream is) throws CompileException, IOException {
        this.cook(fileName, is, null);
    }

    @Override
    public final void cook(InputStream is, @Nullable String encoding) throws CompileException, IOException {
        this.cook(encoding == null ? new InputStreamReader(is) : new InputStreamReader(is, encoding));
    }

    @Override
    public final void cook(@Nullable String fileName, InputStream is, @Nullable String encoding) throws CompileException, IOException {
        this.cook(fileName, encoding == null ? new InputStreamReader(is) : new InputStreamReader(is, encoding));
    }

    @Override
    public final void cook(String s2) throws CompileException {
        this.cook((String)null, s2);
    }

    @Override
    public final void cook(@Nullable String fileName, String s2) throws CompileException {
        try {
            this.cook(fileName, new StringReader(s2));
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException("SNO: StringReader throws IOException");
        }
    }

    @Override
    public final void cookFile(File file) throws CompileException, IOException {
        this.cookFile(file, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void cookFile(File file, @Nullable String encoding) throws CompileException, IOException {
        FileInputStream is = new FileInputStream(file);
        try {
            this.cook(file.getAbsolutePath(), encoding == null ? new InputStreamReader(is) : new InputStreamReader((InputStream)is, encoding));
            ((InputStream)is).close();
            is = null;
        }
        finally {
            if (is != null) {
                try {
                    ((InputStream)is).close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    @Override
    public final void cookFile(String fileName) throws CompileException, IOException {
        this.cookFile(fileName, null);
    }

    @Override
    public final void cookFile(String fileName, @Nullable String encoding) throws CompileException, IOException {
        this.cookFile(new File(fileName), encoding);
    }
}

