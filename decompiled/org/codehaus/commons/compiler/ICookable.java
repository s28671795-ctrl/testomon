/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.nullanalysis.Nullable;

public interface ICookable {
    public void cook(@Nullable String var1, Reader var2) throws CompileException, IOException;

    public void cook(Reader var1) throws CompileException, IOException;

    public void cook(InputStream var1) throws CompileException, IOException;

    public void cook(@Nullable String var1, InputStream var2) throws CompileException, IOException;

    public void cook(InputStream var1, @Nullable String var2) throws CompileException, IOException;

    public void cook(@Nullable String var1, InputStream var2, @Nullable String var3) throws CompileException, IOException;

    public void cook(String var1) throws CompileException;

    public void cook(@Nullable String var1, String var2) throws CompileException;

    public void cookFile(File var1) throws CompileException, IOException;

    public void cookFile(File var1, @Nullable String var2) throws CompileException, IOException;

    public void cookFile(String var1) throws CompileException, IOException;

    public void cookFile(String var1, @Nullable String var2) throws CompileException, IOException;

    public void setSourceVersion(int var1);

    public void setTargetVersion(int var1);

    public Map<String, byte[]> getBytecodes();
}

