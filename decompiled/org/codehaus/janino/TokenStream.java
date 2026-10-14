/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.IOException;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Token;
import org.codehaus.janino.TokenType;

public interface TokenStream {
    public Token peek() throws CompileException, IOException;

    public boolean peek(String var1) throws CompileException, IOException;

    public int peek(String ... var1) throws CompileException, IOException;

    public boolean peek(TokenType var1) throws CompileException, IOException;

    public int peek(TokenType ... var1) throws CompileException, IOException;

    public Token peekNextButOne() throws CompileException, IOException;

    public boolean peekNextButOne(String var1) throws CompileException, IOException;

    public Token read() throws CompileException, IOException;

    public void read(String var1) throws CompileException, IOException;

    public int read(String ... var1) throws CompileException, IOException;

    public String read(TokenType var1) throws CompileException, IOException;

    public int read(TokenType ... var1) throws CompileException, IOException;

    public boolean peekRead(String var1) throws CompileException, IOException;

    public int peekRead(String ... var1) throws CompileException, IOException;

    @Nullable
    public String peekRead(TokenType var1) throws CompileException, IOException;

    public int peekRead(TokenType ... var1) throws CompileException, IOException;

    public void setWarningHandler(@Nullable WarningHandler var1);

    public Location location();
}

