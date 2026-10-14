/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.codehaus.commons.compiler.CompileException;

public interface IMultiCookable {
    public void cook(Reader ... var1) throws CompileException, IOException;

    public void cook(String[] var1, Reader[] var2) throws CompileException, IOException;

    public void cook(String[] var1) throws CompileException;

    public void cook(String[] var1, String[] var2) throws CompileException;

    public void cook(InputStream ... var1) throws CompileException, IOException;

    public void cook(InputStream[] var1, String[] var2) throws CompileException, IOException;

    public void cook(String[] var1, InputStream[] var2) throws CompileException, IOException;

    public void cook(String[] var1, InputStream[] var2, String[] var3) throws CompileException, IOException;

    public void cookFiles(File[] var1) throws CompileException, IOException;

    public void cookFiles(File[] var1, String[] var2) throws CompileException, IOException;

    public void cookFiles(String[] var1) throws CompileException, IOException;

    public void cookFiles(String[] var1, String[] var2) throws CompileException, IOException;
}

